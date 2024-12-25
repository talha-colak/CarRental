package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.dto.RentalStatus;
import com.talhacolak.carrental.entity.Car;
import com.talhacolak.carrental.entity.Customer;
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
            Query<Rental> query = session.createQuery("from Rental where rentalStatus = :status", Rental.class);
            query.setParameter("status", RentalStatus.ONGOING);
            return query.list();

        } catch (Exception e) {
            System.err.println("Bulunamadı " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Rental findRentedInfoByPlate(String licensePlate) { // arg , String licenseNumber
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Rental> query = session.createQuery("from Rental where car.licensePlate = :plate", Rental.class);
            query.setParameter("plate", licensePlate);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Bulunamadı " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Customer getCustomerByCar(Rental selectedRental, RentalStatus rentalStatus) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Rental> query = session.createQuery("from Rental where car.id = :selectedRental AND rentalStatus = :status", Rental.class);
            query.setParameter("selectedRental", selectedRental.getCar().getId());
            query.setParameter("status", rentalStatus);

            Rental rental = query.uniqueResult();
            return rental != null ? rental.getCustomer() : null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void updateRental(Rental rental, Car car) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            session.update(rental);

            Car rentedCar = rental.getCar();

            session.update(rentedCar);

            session.getTransaction().commit();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}