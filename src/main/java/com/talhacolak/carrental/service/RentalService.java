package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Rental;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RentalService {

    public Rental save(Session session, Rental rental) {
//        Transaction transaction = null;
        Transaction transaction = session.getTransaction();
        boolean newTransaction = false;

        try {
            if (!transaction.isActive()) {
                transaction = session.beginTransaction();
                newTransaction = true;
            }
            session.persist(rental);
            System.out.println("Kiralama işlemi yapıldı!" + rental);
            if (newTransaction) {
                transaction.commit();
            }
            return rental;

        } catch (Exception e) {
            if (newTransaction && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            System.err.println("Kiralama işlemi yapılamadı!" + e.getMessage());
            e.printStackTrace();
            return null;
        }

       /* try {
            System.out.println("Rental SessionFactory is open: " + HibernateUtil.getSessionFactory().isOpen());
            transaction = session.beginTransaction();
            session.persist(rental);
//          session.save(rental);
//          transaction.commit();

            System.out.println("Kiralama işlemi yapıldı!" + rental);
            return rental;

        } catch (Exception e) {
           *//* if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
           *//*
            System.err.println("Kiralama işlemi yapılamadı!" + e.getMessage());
            e.printStackTrace();
            return null;
        }*/
    }
}