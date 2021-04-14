package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/MyDB?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    public static SessionFactory getHibernateSession() {

        Properties prop = new Properties();
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        prop.setProperty("hibernate.connection.username", "root");
        prop.setProperty("hibernate.connection.password", "root");
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mydb");
        prop.setProperty("show_sql", "true");


        // Session session = null;

        SessionFactory sessionFactory = new AnnotationConfiguration()
                .addPackage("jm.task.core.jdbc.model")
                .addProperties(prop)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        sessionFactory.openSession();
        return sessionFactory;
    }

    public static Connection getJDBCConnection() {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Не удалось загрузить класс драйвера!");
        }
        return connection;
    }
}

