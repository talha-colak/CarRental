package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Car;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CarService {


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

    public static Car findCarByPlate(String licensePlate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Car> query = session.createQuery("from Car where licensePlate = :plate", Car.class);
            query.setParameter("plate", licensePlate);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Araba Bulunamadı " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public List<Car> getAllCars() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Car", Car.class).list();

        }
    }
}
