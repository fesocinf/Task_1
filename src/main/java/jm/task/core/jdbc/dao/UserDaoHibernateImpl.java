package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Session session;
    public UserDaoHibernateImpl() {
        session = Util.getSession();
    }


    @Override
    public void createUsersTable() {
        session.beginTransaction();

        session.createSQLQuery("CREATE TABLE User (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR(64), lastName VARCHAR(64), age TINYINT)").addEntity(User.class).executeUpdate();;

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        session.beginTransaction();

        session.createSQLQuery("DROP TABLE IF EXISTS User").addEntity(User.class).executeUpdate();;

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        return session.createQuery("from Student", User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        session.getTransaction().commit();
    }
}
