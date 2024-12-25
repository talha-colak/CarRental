package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Inspection;
import com.talhacolak.carrental.entity.Rental;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CarInspectionService {

    public Inspection save(Session session, Inspection inspection) {

        try {
            System.out.println("SessionFactory is open: " + HibernateUtil.getSessionFactory().isOpen());
            session.persist(inspection);

            System.out.println("İnceleme bilgileri kaydedildi!" + inspection);
            return inspection;

        } catch (Exception e) {
            System.err.println("İnceleme bilgileri kaydedilemedi: " + inspection);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void saveInspectionWithCar(Rental selectedRental, Inspection inspection) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(inspection);
            session.merge(selectedRental);

            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}