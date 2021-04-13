package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getJDBCConnection();
        String sql = "create table `mydb_test`.user\n" +
                "(\n" +
                "    id       int         not null\n" +
                "        primary key,\n" +
                "    name varchar(40) not null,\n" +
                "    lastName varchar(40) not null,\n" +
                "    age int not null )";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getJDBCConnection();
        String sql = "drop table `mydb_test`.user";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getJDBCConnection();
        String countSql = "SELECT COUNT(*) FROM mydb_test.user";
        String insertSql = "INSERT INTO mydb_test.user(id, name, lastName, age) VALUES (?, ? ,?, ?)";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(countSql);
            resultSet.next();
            long id  = resultSet.getLong(1)+1;
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setByte(4, age);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getJDBCConnection();
        String removeSql = "DELETE FROM mydb_test.user WHERE id = (?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(removeSql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection connection = Util.getJDBCConnection();
        String sql = "SELECT * FROM mydb_test.user";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getJDBCConnection();
        String cleanSql = "TRUNCATE TABLE mydb_test.user;";
        try {
            Statement statement = connection.createStatement();
            boolean execute = statement.execute(cleanSql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
