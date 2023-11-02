/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  

/**
 *
 * @author User
 */
public class RegistForm extends JPanel{
    public static boolean isRegistered = false;
    
    private JTextField usernameTextField;
    private JTextField fullnameTextField;
    private JPasswordField passwordTextField;
    
    private JButton registerButton;
    
    public RegistForm() {
        // instantiate layout class
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Username TextField
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0 ));
        JLabel label = new JLabel("Username:");
        label.setForeground(new Color(255, 255, 255));
        usernameTextField = new JTextField(20);
        usernameTextField.setPreferredSize(new Dimension(200, 40));
        usernameTextField.setFont(new Font("Arial", Font.PLAIN, 16)); // Ganti jenis huruf dan ukuran
        usernameTextField.setBackground(new Color(30, 30, 30)); // Latar belakang hitam
        usernameTextField.setForeground(new Color(255, 255, 255)); // Warna teks putih
        usernameTextField.setCaretColor(new Color(255, 255, 255)); // Warna kursor putih
        usernameTextField.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 2, true)); // Border dengan warna dan ketebalan tertentu
        usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                usernameTextField.setBorder(BorderFactory.createLineBorder(new Color(29, 185, 84), 2, true)); // Border berwarna saat fokus
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameTextField.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 2, true)); // Border awal saat hilang fokus
            }
        });
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(usernameTextField, BorderLayout.CENTER);
        
         // Fullname TextField
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.setBackground(new Color(0, 0, 0));
        panel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0 ));
        JLabel label1 = new JLabel("Fullname");
        label1.setForeground(new Color(255, 255, 255));
        fullnameTextField = new JTextField(20);
        fullnameTextField.setPreferredSize(new Dimension(200, 40));
        fullnameTextField.setFont(new Font("Arial", Font.PLAIN, 16)); // Ganti jenis huruf dan ukuran
        fullnameTextField.setBackground(new Color(30, 30, 30)); // Latar belakang hitam
        fullnameTextField.setForeground(new Color(255, 255, 255)); // Warna teks putih
        fullnameTextField.setCaretColor(new Color(255, 255, 255)); // Warna kursor putih
        fullnameTextField.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 2, true)); // Border dengan warna dan ketebalan tertentu
        fullnameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fullnameTextField.setBorder(BorderFactory.createLineBorder(new Color(29, 185, 84), 2, true)); // Border berwarna saat fokus
            }

            @Override
            public void focusLost(FocusEvent e) {
                fullnameTextField.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 2, true)); // Border awal saat hilang fokus
            }
        });
        
        panel1.add(label1, BorderLayout.NORTH);
        panel1.add(fullnameTextField, BorderLayout.CENTER);
        
        // Password TextField
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setBackground(new Color(0, 0, 0));
        panel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0 ));
        JLabel label2 = new JLabel("Password");
        label2.setForeground(new Color(255, 255, 255));
        passwordTextField = new JPasswordField(20);
        passwordTextField.setPreferredSize(new Dimension(200, 40));
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 16)); // Ganti jenis huruf dan ukuran
        passwordTextField.setBackground(new Color(30, 30, 30)); // Latar belakang hitam
        passwordTextField.setForeground(new Color(255, 255, 255)); // Warna teks putih
        passwordTextField.setCaretColor(new Color(255, 255, 255)); // Warna kursor putih
        passwordTextField.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 2, true)); // Border dengan warna dan ketebalan tertentu
        passwordTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordTextField.setBorder(BorderFactory.createLineBorder(new Color(29, 185, 84), 2, true)); // Border berwarna saat fokus
            }

            @Override
            public void focusLost(FocusEvent e) {
                passwordTextField.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 2, true)); // Border awal saat hilang fokus
            }
        });
        
        panel2.add(label2, BorderLayout.NORTH);
        panel2.add(passwordTextField, BorderLayout.CENTER);
        
        //  Button
        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(200, 40));
        registerButton.setFont(new Font("Arial", Font.PLAIN, 16));
        registerButton.setForeground(new Color(255, 255, 255));
        registerButton.setBackground(new Color(29, 185, 84)); // Warna latar belakang hijau
        
        // Set JFrame
        setLayout(grid);
        gbc.gridy = 0;
        add(panel, gbc);
        gbc.gridy = 1;
        add(panel1, gbc);
        gbc.gridy = 2;
        add(panel2, gbc);
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(registerButton, gbc);
        setBackground(new Color(0,0,0));
    }
    
    // getters
    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getFullname() {
        return fullnameTextField.getText();
    }
    
    public String getPassword() {
        return passwordTextField.getText();
    }

    public void submitUsers(ActionListener actionListener) {
        registerButton.addActionListener(actionListener);
    }

    // reset fields
    public void reset(boolean bln) {
        if(bln) {
            usernameTextField.setText("");
            fullnameTextField.setText("");
            passwordTextField.setText("");
        }
    }
    
    public static void main(String[] args) {
        RegistForm registForm = new RegistForm();
    }
}