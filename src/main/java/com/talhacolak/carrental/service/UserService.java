package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    // Kullanıcı eklemek için metod
    public void addUser(User user) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try (session) {
            transaction = session.beginTransaction();
            session.save(user); // kullanıyı tabloya kaydeder
            transaction.commit();
            System.out.println("Kullanıcı başarıyla eklendi!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Kullanıcı bilgilerini kullanıcı adı ile alan metod
    public User getUserByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = null;

        try {
            // kullanıcıyı seçmek için sorgu
            user = session.createQuery("FROM User WHERE userName = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return user;
    }

}

