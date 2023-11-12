/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.Controller;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.pbo.projectspoti.Model.Database;
import com.pbo.projectspoti.Model.UserModel;
import com.pbo.projectspoti.Model.User;
import com.pbo.projectspoti.View.LoginApp;
import com.pbo.projectspoti.View.RegistForm;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class UserController {
    private Database database;
    private Connection connect;
    private UserModel model;
    private RegistForm registForm;
    private LoginApp loginApp;
    
    public UserController(RegistForm registForm, LoginApp loginApp) {
        // Pengaturan database
        this.database = Database.getInstance();
        this.connect = database.getConnection();
        
        this.model = new UserModel(this.connect);
        this.registForm = registForm;
        this.loginApp = loginApp;
        
        this.registForm.submitUsers(e -> {
            String userid = "user-" + NanoIdUtils.randomNanoId();
            userid = userid.substring(0, Math.min(userid.length(), 15));
            String username = this.registForm.getUsername().trim();
            String fullname = this.registForm.getFullname().trim();
            String password = this.registForm.getPassword().trim();
            
            // Validasi
            if(username.isEmpty()) {
                JOptionPane.showMessageDialog(this.registForm, "Username Required.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(fullname.isEmpty()) {
                JOptionPane.showMessageDialog(this.registForm, "Fullname Required.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(password.isEmpty()) {
                JOptionPane.showMessageDialog(this.registForm, "Password Required.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // accessing database
            try {
                this.model.addUser(new User(userid, username, fullname, password));
            } catch (SQLException er) {
                System.out.println(er.getMessage());
                JOptionPane.showMessageDialog(this.registForm, "Kesalahan Server.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            RegistForm.isRegistered = true;
            
            this.registForm.reset(true);
        });

        this.loginApp.loginUsers(e -> {
            String username = this.loginApp.getUsername().trim();
            String password = this.loginApp.getPassword().trim();
            
            // Check database
            try {
                User user = this.model.getUserByUsername(username);
                String pswd = user.getPassword();
                if(!password.equals(pswd)) {
                    throw new Exception("Password anda salah!");      
                }
                JOptionPane.showMessageDialog(this.loginApp, "Sukses!", "Berhasil",
                        JOptionPane.PLAIN_MESSAGE);
                return;
            } catch (Exception er) {
                System.out.println(er.getMessage());
                JOptionPane.showMessageDialog(this.loginApp, er.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        });
    }
}
