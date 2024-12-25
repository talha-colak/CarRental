package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Car;
import javafx.scene.control.Alert;
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

    public void update(Car car) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(car);

            transaction.commit();
            System.out.println("Araba Bilgileri Güncellendi! " + car);

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Güncellenemedi! " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(Car car) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(car);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Araba Silinemedi: " + e.getMessage());
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Araba Silinemedi", "Araba Kiralanmış", "Araba Silinirken Bir Hata Oluştu!");
        }
    }

    public boolean isCarRented(Car car) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<Long> query = session.createQuery("select count(r) from Rental r where r.car = :car");
            query.setParameter("car", car);
            long rentalCount = query.uniqueResult();
            return rentalCount > 0;
        } catch (Exception e) {
            System.err.println("Hata Oluştu " + e.getMessage());
            e.printStackTrace();
            return false;
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
            return session.createQuery("from Car where status = 'AVAILABLE' ", Car.class).list();
        }
    }

    public void modifyCarStatus(Car car) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.merge(car);
            transaction.commit();
            System.out.println("Araç durumu değiştirildi: " + car.getStatus());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Araç durumu değiştirilemedi" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
