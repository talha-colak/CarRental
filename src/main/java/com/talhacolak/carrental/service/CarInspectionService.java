package com.talhacolak.carrental.service;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Inspection;
import org.hibernate.Session;

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

}