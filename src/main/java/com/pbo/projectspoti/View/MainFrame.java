/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

import com.pbo.projectspoti.Controller.*;
import com.pbo.projectspoti.Helper.EncryptHelper;
import com.pbo.projectspoti.Model.Database;
import com.pbo.projectspoti.Model.User;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class MainFrame extends JFrame {    
    // Ukuran layar
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    
    //  Menyimpan data login pengguna
    public static User loggedUser;
    
    public MainFrame() {
        // Logo program
        Image icon = new ImageIcon("src\\main\\resources\\icons\\spotify.png").getImage();
        this.setIconImage(icon);
        // Load data login pengguna jika ada
        loggedUser = EncryptHelper.loadEncryptedUser("user.txt");
                
        SCREEN_HEIGHT = 700;
        SCREEN_WIDTH = 1200;

        init();
    }

    private void init() {
        // Nama aplikasi
        setTitle("Songtify");
        
        // Ukuran layar
        int FRAME_WIDTH = 1200;
        int FRAME_HEIGHT = 700;
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        
        // Tampilan halaman utama, cek jika sudah login
        if (loggedUser != null) {
            setContentPane(new MainScreen());
        } else {
            setContentPane(new LoginApp());
        }
        FormsManager.getInstance().initApplication(this);
        
        // set listener untuk close database
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Memastikan koneksi ditutup sebelum menutup aplikasi
                Database.getInstance().closeConnection();
            }
        });
        
        // Membuat tampilan center pada layar pengguna
        setLocationRelativeTo(null);

        // Stop program saat exit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menampilkan Jframe ke layar pengguna
        setVisible(true);
    }
}