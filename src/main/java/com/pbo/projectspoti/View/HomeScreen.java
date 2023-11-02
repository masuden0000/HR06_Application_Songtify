/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class HomeScreen extends JPanel {
    public HomeScreen() {
        add(new JLabel("ANDA BERHASIL REGISTER"));
        setBackground(Color.red);
    }
    
    public static void main(String[] args) {
        HomeScreen homeScreen = new HomeScreen();
    }
}
