/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.Model;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author User
 */

public class Database {
    private static final Dotenv dotenv = Dotenv.configure().load();
    
    private static final String JDBC_URL =dotenv.get("JDBC_URL");
    private static final String JDBC_USER = dotenv.get("JDBC_USER");
    private static final String JDBC_PASSWORD = dotenv.get("JDBC_PASSWORD");

    private static Connection connection;
    private static Database instance;
    
    private Database() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}