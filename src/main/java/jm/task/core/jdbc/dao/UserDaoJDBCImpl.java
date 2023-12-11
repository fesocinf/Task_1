package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {
        try (PreparedStatement statement =
                     connection.prepareStatement( "CREATE TABLE User (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                             " name VARCHAR(64), lastName VARCHAR(64), age TINYINT)")) {
            statement.executeUpdate();
        } catch (SQLException ex) { //ignore null table exception
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement("DROP TABLE User")) {
            statement.executeUpdate();
        } catch (SQLException ex) { //ignore null table exception
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO User(name, lastName, age) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM User WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM User"); ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                Byte age = rs.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }

        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM User")) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
    }
}
