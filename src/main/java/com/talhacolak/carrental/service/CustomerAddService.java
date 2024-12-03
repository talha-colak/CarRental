package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerAddService {

    public void save(Customer customer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
            System.out.println("Müşteri başarıyla kaydedildi!" + customer);

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Müşteri kaydedilemedi!" + e.getMessage());
            e.printStackTrace();
        }
    }

    public Customer findCustomerById(String licenseNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Customer> query = session.createQuery("from Customer where licenseNumber = :license", Customer.class);
            query.setParameter("license", licenseNumber);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Müşteri Bulunamadı" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Customer> getAllCustomer() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Customer ", Customer.class).list();
        } catch (Exception e) {
            System.err.println("Müşteriler Getirilemedi" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}