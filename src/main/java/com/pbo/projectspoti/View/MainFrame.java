/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

import com.pbo.projectspoti.Controller.UserController;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author User
 */
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    public MainFrame() {
        super("Spotify");
        cardLayout = new CardLayout();
        RegistForm registForm = new RegistForm();
        HomeScreen homeScreen = new HomeScreen();
        MusicPlayer musicPlayer = new MusicPlayer();
        LoginApp loginApp = new LoginApp();
        
        setLayout(cardLayout);
        
        new UserController(registForm, loginApp, cardLayout, MainFrame.this.getContentPane());
        
//        add(musicPlayer);
        add(loginApp);
        add(registForm, "RegisterForm");
        add(homeScreen, "HomeScreen");
        
        // frame width & height
        int FRAME_WIDTH = 1200;
        int FRAME_HEIGHT = 700;
        // size of our application frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
