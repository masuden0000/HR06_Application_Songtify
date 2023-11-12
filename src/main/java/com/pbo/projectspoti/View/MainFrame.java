/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

import com.pbo.projectspoti.Controller.MusicController;
import com.pbo.projectspoti.Controller.*;
import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        init();

        // Logo program
        Image icon = new ImageIcon("src\\main\\resources\\icons\\spotify.png").getImage();
        this.setIconImage(icon);
    }

    private void init() {
        // Nama aplikasi
        setTitle("Spotify");

        // Membuat objek RegistForm dan LoginApp
        RegistForm registForm = new RegistForm();
        LoginApp loginApp = new LoginApp();

        // Ukuran layar
        int FRAME_WIDTH = 1200;
        int FRAME_HEIGHT = 700;
        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        // Tampilan halaman utama
        setContentPane(new LoginApp());
        FormsManager.getInstance().initApplication(this);

        // Membuat tampilan center pada layar pengguna
        setLocationRelativeTo(null);

        // Stop program saat exit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menampilkan Jframe ke layar pengguna
        setVisible(true);

        // User controler
        new UserController(registForm, loginApp);
    }
}
