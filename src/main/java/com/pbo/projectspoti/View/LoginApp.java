/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

/**
 *
 * @author Lenovo
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  
import javax.swing.border.EmptyBorder;

public class LoginApp extends JPanel {
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    
    private JButton loginButton;
    
    public LoginApp() {
        // instantiate layout class
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        
        // header
        JPanel panelHead = new JPanel(new BorderLayout());
        panelHead.setBackground(new Color(0, 0, 0));
        panelHead.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0 ));
        JLabel labelHead = new JLabel("Login");
        labelHead.setForeground(new Color(255, 255, 255));
        labelHead.setFont(new Font("Arial", Font.BOLD, 24));
        
        panelHead.add(labelHead, BorderLayout.NORTH);
        
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
        
        // Password TextField
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.setBackground(new Color(0, 0, 0));
        panel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0 ));
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
        
        panel1.add(label2, BorderLayout.NORTH);
        panel1.add(passwordTextField, BorderLayout.CENTER);
        
        //  Button
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setForeground(new Color(0, 0, 0));
        loginButton.setBackground(new Color(255, 255, 255)); // Warna latar belakang hijau
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(29, 185, 84));
                loginButton.setForeground(new Color(255, 255, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(255, 255, 255));
                loginButton.setForeground(new Color(0, 0, 0));
            }
        });
        
        // Set JFrame
        setLayout(grid);
        gbc.gridy = 0;
        add(panelHead, gbc);
        gbc.gridy = 1;
        add(panel, gbc);
        gbc.gridy = 2;
        add(panel1, gbc);
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(loginButton, gbc);
        setBackground(new Color(0, 0, 0));
    }
    
    public String getUsername() {
        return usernameTextField.getText();
    }
    
    public String getPassword() {
        return passwordTextField.getText();
    }
    
    public void loginUsers(ActionListener actionListener) {
        loginButton.addActionListener(actionListener);
    }
    
    public static void main(String[] args) {
        LoginApp login = new LoginApp();
    }
}
