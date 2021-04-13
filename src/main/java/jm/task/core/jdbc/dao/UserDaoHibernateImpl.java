package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getHibernateSession();
        String sql = "create table `MyDB`.user_table\n" +
                "(\n" +
                "    id       int         not null\n" +
                "        primary key,\n" +
                "    name varchar(40) not null,\n" +
                "    lastName varchar(40) not null,\n" +
                "    age int not null )";
        try {
            session.getTransaction().begin();
            NativeQuery sqlQuery = session.createNativeQuery(sql);
            sqlQuery.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getHibernateSession();
        String sql = "drop table `MyDB`.user_table";
        try {
            session.getTransaction().begin();
            NativeQuery sqlQuery = session.createNativeQuery(sql);
            sqlQuery.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getHibernateSession();
        try {
            session.getTransaction().begin();
            int lastId = session.createQuery("FROM User").getResultList().size();
            User user = new User(name, lastName, age);
            user.setId((long) ++lastId);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getHibernateSession();
        try {
            session.getTransaction().begin();
            Query query = session.createQuery("DELETE FROM User where id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getHibernateSession();
        try {
            Query<User> userQuery = session.createQuery("FROM User", User.class);
            return userQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getHibernateSession();
        try {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM User");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
