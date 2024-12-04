package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Inspection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarInspectionService {

    public void save(Inspection inspection) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //session açık mı değil mi diye kontrol
            System.out.println("SessionFactory is open: " + HibernateUtil.getSessionFactory().isOpen());
            transaction = session.beginTransaction();
            session.save(inspection);
            transaction.commit();
            System.out.println("İnceleme bilgileri kaydedildi!" + inspection);

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("İnceleme bilgileri kaydedilemedi: " + inspection);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Inspection> getAllInspections() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Inspection ", Inspection.class).list();
        } catch (Exception e) {
            System.err.println("İnceleme bilgileri getirilemedi" + e.getMessage());
            e.printStackTrace();
        }
        return List.of();
    }
}