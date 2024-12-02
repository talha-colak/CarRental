package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Car;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CarAddService {

    public void save(Car car) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.save(car);

            transaction.commit();
            System.out.println("Araç başarıyla eklendi!" + car);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Araç kaydedilemedi!" + e.getMessage());
            e.printStackTrace();
        }
    }
}
