package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.imageio.spi.ServiceRegistry;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final Connection connection;
    private static final Session session;
    private static final Properties properties;
    private static final String hostName;
    private static final String dbName;
    private static final String password;
    private static final String userName;
    private static final String connectionString;

    static {
        try {
            properties = new Properties();
            loadProperties(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        hostName = properties.getProperty("hostName");
        dbName = properties.getProperty("dbName");
        userName = properties.getProperty("username");
        password = properties.getProperty("password");
        connectionString = "jdbc:mysql://" + hostName + ":3306/" + dbName;
    }
    static {
        Configuration configuration = new Configuration();

        Properties hibernateProperties = new Properties();
        hibernateProperties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        hibernateProperties.put(Environment.URL, connectionString);
        hibernateProperties.put(Environment.USER, userName);
        hibernateProperties.put(Environment.PASS, password);
        hibernateProperties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.put(Environment.SHOW_SQL, "true");
        hibernateProperties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        hibernateProperties.put(Environment.HBM2DDL_AUTO, "");

        configuration.setProperties(hibernateProperties);

        configuration.addAnnotatedClass(User.class);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

        session = configuration.buildSessionFactory(builder.build()).openSession();
    }

    static {
        try {
            connection = getMySQLConnection(hostName, dbName, userName, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
    public static Session getSession() {
        return session;
    }
    public static void loadProperties(Properties properties) throws IOException {
        try(InputStream in = Files.newInputStream(Paths.get("src/main/java/jm/task/core/jdbc/util/database.properties"))){
            properties.load(in);
        }
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException,
            ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");

        return DriverManager.getConnection(connectionString, userName,
                password);
    }

    public static void closeMySQLConnection() throws SQLException {
        if (connection!=null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static void closeHibernateSession() {
        if (session!=null && session.isOpen()) {
            session.close();
        }
    }
}
