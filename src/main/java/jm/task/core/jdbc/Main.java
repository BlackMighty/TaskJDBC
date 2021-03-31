package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLOutput;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        String name = "Vasya";
        userService.saveUser(name, "Ivanov", (byte) 25);
        System.out.println("Пользователья добавлен успешно " +  name);
        name = "Petya";
        userService.saveUser(name, "Petrov", (byte) 30);
        System.out.println("Пользователья добавлен успешно " + name);
        name = "Andrey";
        userService.saveUser(name, "Zolotarev", (byte) 28);
        System.out.println("Пользователья добавлен успешно " + name);
        name = "Roman";
        userService.saveUser(name, "Petrov", (byte) 31);
        System.out.println("Пользователья добавлен успешно " + name);

        List<User> allUsers = userService.getAllUsers();
        for (int i = 0; i < allUsers.size(); i++){
            User user = allUsers.get(i);
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
