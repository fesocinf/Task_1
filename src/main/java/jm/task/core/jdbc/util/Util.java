package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException, IOException {
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("src/main/java/jm/task/core/jdbc/util/database.properties"))){
            props.load(in);
        }
        String hostName = props.getProperty("hostName");
        String dbName = props.getProperty("dbName");
        String userName = props.getProperty("username");
        String password = props.getProperty("password");

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException,
            ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        return DriverManager.getConnection(connectionURL, userName,
                password);
    }
}
