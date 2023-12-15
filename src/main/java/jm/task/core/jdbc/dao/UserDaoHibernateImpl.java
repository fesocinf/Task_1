package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;
    Transaction tx1;
    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            tx1 = session.beginTransaction();

            session.createSQLQuery("CREATE TABLE User (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    " name VARCHAR(64), lastName VARCHAR(64), age TINYINT)").addEntity(User.class).executeUpdate();

            tx1.commit();
        } catch (Exception ignored) {
            tx1.rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            tx1 = session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS User").addEntity(User.class).executeUpdate();

            tx1.commit();
        } catch (Exception ignored) {
            tx1.rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            User user = new User(name, lastName, age);
            Transaction tx1 = session.beginTransaction();
            session.save(user);
            tx1.commit();
        } catch (Exception e) {
            tx1.rollback();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            tx1.commit();
        } catch (Exception e) {
            tx1.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            tx1.commit();
        } catch (Exception e) {
            tx1.rollback();
        }
    }
}
