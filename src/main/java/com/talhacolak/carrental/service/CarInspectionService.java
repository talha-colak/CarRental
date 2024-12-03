package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Car;
import com.talhacolak.carrental.entity.Inspection;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

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

    public Car findInspectionByPlate(String licensePlate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Car> query = session.createQuery("from Car where licensePlate = :plate ", Car.class);
            query.setParameter("plate", licensePlate);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Inspection> getAllInspections() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Inspection ", Inspection.class).list();
        } catch (Exception e) {
            System.err.println("İnceleme bilgileri getirilemedi" + e.getMessage());
        }
        return List.of();
    }
}