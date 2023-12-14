package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {

        List<User> userList = new ArrayList<>() {
            {
                {
                    add(new User("DIMA", "RYABKOV", (byte) 19));
                    add(new User("Andrey", "Semchenko", (byte) 11));
                    add(new User("Name", "Erjanov", (byte) 22));
                    add(new User("Fantasy", "Null", (byte) 50));
                }
            }
        };

        userService.createUsersTable();

        for (User user: userList) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("User с именем – %s добавлен в базу данных%n", user.getName());
        }

        System.out.println("Все полученные пользователи:");
        userList = userService.getAllUsers();
        for (User user: userList) {
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.closeHibernateSession();
    }
}
