package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Car;
import com.talhacolak.carrental.entity.Inspection;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

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

    public List<Inspection> getInspectionValues(Car selectedCar) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Car> query = session.createQuery("select c from Car c Left join fetch Car.inspectionList where c.id = :carId", Car.class);
            query.setParameter("carId", selectedCar.getId());
//            selectedCar.getInspectionList()
            Car carWithInspections = query.uniqueResult();

            if (carWithInspections != null) {
                return carWithInspections.getInspectionList();
            } else {
                System.err.println("Araba verilen id ile bulunamadı");
                return new ArrayList<>();
            }

        } catch (Exception e) {
            System.err.println("Müşteri Bulunamadı " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}