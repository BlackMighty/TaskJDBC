package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getHibernateSession();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        String sql = "create table `MyDB`.user_table\n" +
                "(\n" +
                "    id       int         not null\n" +
                "        primary key,\n" +
                "    name varchar(40) not null,\n" +
                "    lastName varchar(40) not null,\n" +
                "    age int not null )";
        session.getTransaction().begin();
        NativeQuery sqlQuery = session.createNativeQuery(sql);
        sqlQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        String sql = "drop table `MyDB`.user_table";
        session.getTransaction().begin();
        NativeQuery sqlQuery = session.createNativeQuery(sql);
        sqlQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        int lastId = session.createQuery("FROM User").getResultList().size();
        User user = new User(name, lastName, age);
        user.setId((long) ++lastId);
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM User where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Query<User> userQuery = session.createQuery("FROM User", User.class);
        return userQuery.getResultList();
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.createQuery("DELETE FROM User");
        session.getTransaction().commit();
        session.close();
    }
}
