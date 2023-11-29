/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.Controller;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.pbo.projectspoti.Helper.EncryptHelper;
import com.pbo.projectspoti.Model.Database;
import com.pbo.projectspoti.Model.UserModel;
import com.pbo.projectspoti.Model.User;
import com.pbo.projectspoti.View.LoginApp;
import com.pbo.projectspoti.View.MainFrame;
import com.pbo.projectspoti.View.MainScreen;
import com.pbo.projectspoti.View.RegistForm;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class UserController {
    private Database database;
    private Connection connect;
    private UserModel model;
    
    public UserController() {
        // Pengaturan database
        this.database = Database.getInstance();
        this.connect = database.getConnection();
        
        this.model = new UserModel(this.connect);            
    }
    
    public boolean register(String username, String fullname, String password, String confirmPassword) {
        String userid = "user-" + NanoIdUtils.randomNanoId();
        userid = userid.substring(0, Math.min(userid.length(), 15));
        username = username.trim();
        fullname = fullname.trim();
        password = password.trim();

        // Validasi
        if(fullname.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fullname Required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username Required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Password Required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Confirm Password Required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Password Mismatched.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // accessing database
        try {
            this.model.addUser(new User(userid, username, fullname, password));
            FormsManager.getInstance().showForm(new LoginApp());
            return true;
        }  catch (SQLException er) {
            if (er.getSQLState().equals("23505")) {
                JOptionPane.showMessageDialog(null, "Username sudah digunakan", "Error",
                JOptionPane.ERROR_MESSAGE);
                return false;
            }
            JOptionPane.showMessageDialog(null, "Kesalahan Server.", "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Kesalahan Server.", "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean login(String username, String password) {
        username = username.trim();
        password = password.trim();
        
         // Validasi
        if(username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username Required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Password Required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Check database
        try {
            User user = this.model.getUserByUsername(username);
            String pswd = user.getPassword();
            if(!password.equals(pswd)) {
                JOptionPane.showMessageDialog(null, "Wrong Password", "Error",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            ImageIcon checklist = new ImageIcon("src\\main\\resources\\icons\\checklist.png");
            JOptionPane.showMessageDialog(null, "Sukses! mohon tunggu..", "Berhasil",
                    JOptionPane.PLAIN_MESSAGE, checklist);
            EncryptHelper.saveEncryptedUser(user, "user.txt");

            User loadedUser = EncryptHelper.loadEncryptedUser("user.txt");
            
            MainFrame.loggedUser = loadedUser;
            
            FormsManager.getInstance().showForm(new MainScreen());
            
            return true;
        } catch (Exception er) {
            System.out.println(er.getMessage());
            JOptionPane.showMessageDialog(null, "Kesalahan Server", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
