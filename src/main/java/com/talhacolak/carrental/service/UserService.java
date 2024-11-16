package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserService {

    // Method to add a user
    public void addUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try (session) {
            transaction = session.beginTransaction();
            session.save(user); // Save the user entity
            transaction.commit();
            System.out.println("User added successfully!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Method to retrieve a user by username
    public User getUserByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = null;

        try {
            // Query to find the user
            user = session.createQuery("FROM User WHERE userName = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return user; // Return the user or null if not found
    }

}

