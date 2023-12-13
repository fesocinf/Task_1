package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Session session;
    private final UserDao userDaoJdbc = new UserDaoJDBCImpl();
    public UserDaoHibernateImpl() {
        session = Util.getSession();
    }


    @Override
    public void createUsersTable() {
        userDaoJdbc.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userDaoJdbc.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        User user = new User(name, lastName, age);
        Transaction tx1 = session.beginTransaction();
        session.remove();
        tx1.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
