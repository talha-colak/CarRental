package com.talhacolak.carrental.config;

import java.sql.*;

public class DatabaseConfig {

    private static final String URL = "jdbc:mysql://localhost:3306/veritabani_adi"; // Veritabanı URL
    private static final String USERNAME = "root"; // MySQL kullanıcı adı
    private static final String PASSWORD = "root"; // MySQL şifresi

    private Connection connectDatabase()  {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void createDatabase() {
        try {
            Connection connection = connectDatabase();
            Statement statement = connection.createStatement();
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS CarRental";
            int result = statement.executeUpdate(createDatabaseQuery);

            if (result == 0) {
                System.out.println("Veritabanı başarıyla oluşturuldu veya zaten mevcut.");

                String useDatabaseQuery = "USE CarRental";
                statement.executeUpdate(useDatabaseQuery);
                String createCarTableQuery = "CREATE TABLE IF NOT EXISTS Car (id INT AUTO_INCREMENT PRIMARY KEY, brand VARCHAR(50), model VARCHAR(50),fuel ENUM('Benzinli', 'Dizel', 'Hibrit' DEFAULT 'Beklemede') , gear ENUM('Manuel', 'Otomatik' DEFAULT 'Beklemede'), year INT, plate VARCHAR(50), price INT, status ENUM('Müsait', 'Kirada', 'Bakımda') DEFAULT 'Beklemede')";
                String createUserTableQuery = "CREATE TABLE IF NOT EXISTS User ( id INT AUTO_INCREMENT PRIMARY KEY, roles ENUM('Yönetici','Çalışan', )";


            } else {
                System.out.println("Veritabanı oluşturulurken bir hata oluştu.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /*Connection connection = connectDatabase();
    Statement statement = connection.createStatement();

    // Veritabanını kullan
    String useDatabaseQuery = "USE CarRental";
    statement.executeUpdate(useDatabaseQuery);

    // Tablo oluşturma sorgusu
    String createTableQuery = "CREATE TABLE IF NOT EXISTS Araclar
            (" + "id INT AUTO_INCREMENT PRIMARY KEY," +
            "marka VARCHAR(50)," +
            "model VARCHAR(50)," +
            "yil INT" +
            ")";

    statement.executeUpdate(createTableQuery);

    System.out.println("Araclar tablosu başarıyla oluşturuldu veya zaten mevcut.");

    // Kaynakları kapat
    statement.close();
    connection.close();*/



    /*Connection connection = connectDatabase();
    String query = "CREATE DATABASE IF NOT EXISTS CarRental";
    Statement statement = connection.createStatement();

    // DDL komutları için executeUpdate() kullanılır, çünkü ResultSet dönmez
    int result = statement.executeUpdate(query);

    // Sonucun başarılı olup olmadığını kontrol edebilirsiniz
    if (result == 0) {
        System.out.println("Veritabanı başarıyla oluşturuldu veya zaten mevcut.");
    } else {
        System.out.println("Veritabanı oluşturulurken bir hata oluştu.");
    }

    statement.close();
    connection.close();
    */







    /*try {
        // MySQL'e bağlanmak için bağlantıyı aç
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Bağlantı başarılı!");

        // SQL sorgusu oluştur ve çalıştır
        String query = "SELECT * FROM tablo_adi";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        // Sonuçları işleme
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id"));
            System.out.println("İsim: " + resultSet.getString("isim"));
        }

        // Kaynakları kapat
        resultSet.close();
        statement.close();
        connection.close();
    } catch (Exception e) {
        e.printStackTrace();
    }*/
}
