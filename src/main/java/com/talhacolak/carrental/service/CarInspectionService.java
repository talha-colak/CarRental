package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Inspection;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CarInspectionService {

    public void save(Inspection inspection) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(inspection);
            transaction.commit();
            System.out.println("İnceleme bilgileri kaydedildi!" + inspection);

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("İnceleme bilgileri kaydedilemedi!" + e.getMessage());
            e.printStackTrace();
        }
    }

}
