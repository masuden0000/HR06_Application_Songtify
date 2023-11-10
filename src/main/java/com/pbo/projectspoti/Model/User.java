/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.Model;

/**
 *
 * @author User
 */
public class User {
    private String userId;
    private String username;
    private String fullName;
    private String password;



    public User(String userId, String username, String fullName, String password) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
    }

    User() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // getters
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }
    
    // setters
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
