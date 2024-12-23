package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.dto.RentalStatus;
import com.talhacolak.carrental.entity.Rental;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

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
    }

    public List<Rental> getRentedCars() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Rental> query = session.createQuery(
                    "from Rental where rentalStatus = :status", Rental.class);
            query.setParameter("status", RentalStatus.ONGOING);
            return query.list();

        } catch (Exception e) {
            System.err.println("Bulunamadı " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Rental findRentedInfoByPlate(String licensePlate, String licenseNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Rental> query = session.createQuery("from Rental where car.licensePlate = :plate", Rental.class);
            query.setParameter("license", licenseNumber);
            /*Query<Rental> query = session.createQuery("from Rental where car.licensePlate = :plate OR customer.licenseNumber = :license", Rental.class);
            query.setParameter("plate", licensePlate);*/
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Bulunamadı " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}