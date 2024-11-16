package com.talhacolak.carrental.config;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // SessionFactory'i hibernate.cfg.xml dosyasından oluştur
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory oluşturulamadı." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

//    public static void shutdown() {
//        getSessionFactory().close();
//    }
}
