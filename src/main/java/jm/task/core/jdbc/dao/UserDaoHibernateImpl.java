package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session= Util.getSessionFactory().openSession();
        Transaction transaction=null;
        try {
            transaction = session.beginTransaction();

            // SQL для создания таблицы
            String createTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGSERIAL PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                lastName VARCHAR(50) NOT NULL,
                age SMALLINT
            )
            """;

            // Выполнение нативного SQL
            session.createNativeQuery(createTableSQL).executeUpdate();

            transaction.commit();
            System.out.println("Table created successfully with SQL!");

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session= Util.getSessionFactory().openSession();
        Transaction transaction=null;
        try {
            transaction = session.beginTransaction();

            // SQL для создания таблицы
            String createTableSQL = """
            Drop table if exists users
            """;

            // Выполнение нативного SQL
            session.createNativeQuery(createTableSQL).executeUpdate();

            transaction.commit();
            System.out.println("Table deleted successfully with SQL!");

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session= Util.getSessionFactory().openSession();
        Transaction transaction=null;
        try {
            transaction = session.beginTransaction();

            // SQL для создания таблицы
            User user = new User(name, lastName, age);
            session.save(user);


            transaction.commit();
            System.out.println("Юзер сохранен");

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session= Util.getSessionFactory().openSession();
        Transaction transaction=null;
        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);

            transaction.commit();
            System.out.println("Table deleted successfully with SQL!");

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session= Util.getSessionFactory().openSession();
        Transaction transaction=null;
        List<User> users=new ArrayList<User>();
        try {
            transaction = session.beginTransaction();


            users = session.createQuery("from jm.task.core.jdbc.model.User", User.class).getResultList();
            transaction.commit();
            System.out.println("Table deleted successfully with SQL!");

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session= Util.getSessionFactory().openSession();
        Transaction transaction=null;
        try {
            transaction = session.beginTransaction();
            session.createQuery("delete from jm.task.core.jdbc.model.User").executeUpdate();

            transaction.commit();
            System.out.println("Table deleted successfully with SQL!");

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
}
