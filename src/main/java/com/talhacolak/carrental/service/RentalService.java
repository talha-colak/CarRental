package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Rental;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RentalService {

    public void save(Rental rental) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(transaction);
            transaction.commit();
            System.out.println("Kiralama işlemi yapıldı!" + rental);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Kiralama işlemi yapılamadı!" + e.getMessage());
            e.printStackTrace();
        }
    }
}
