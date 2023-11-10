/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class UserModel {
    private Connection connection;

    public UserModel(Connection connection) {
        this.connection = connection;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String userId = resultSet.getString("userid");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String fullname = resultSet.getString("fullname");
                // Retrieve other fields as needed

                User user = new User(userId, username, fullname, password);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
    
    public String addUser(User user) throws SQLException {
        String userid = "";
        String sql = "INSERT INTO users VALUES (?, ?, ?, ?) RETURNING userid";
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, user.getUserId());
                statement.setString(2, user.getUsername());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getFullName());
                
               ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    userid = resultSet.getString("userid");
                }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return userid;
    }
    
    public User getUserByUsername(String username) throws SQLException, Exception {
        User user = null;
        String sql = "SELECT * FROM users WHERE username=?";
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, username);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String userId = resultSet.getString("userid");
                String userName = resultSet.getString("username");
                String fullName = resultSet.getString("fullname");
                String password = resultSet.getString("password");
                
                user = new User(userId, userName, fullName, password);  
            } else {
                throw new Exception("Username tidak ditemukan!");
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        
        return user;
    }
}