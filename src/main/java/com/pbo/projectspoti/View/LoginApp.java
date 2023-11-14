package com.pbo.projectspoti.View;

import com.formdev.flatlaf.FlatClientProperties;
import com.pbo.projectspoti.Controller.FormsManager;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginApp extends JPanel {

    public LoginApp() {
        init();
    }

    // GUI
    private void init() {
        // Mengatur layout kontainer utama
        setLayout(new MigLayout("fill", "[center]", "[center]"));

        // Inisiasi komponen
        usernameTextField = new JTextField();
        passwordTextField = new JPasswordField();
        RememberMe = new JCheckBox("Remember me");
        loginButton = new JButton("Login");

        // Membuat kontainer untuk form login
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
        // Memberikan warna panel lebih terang dibanding background-nya sesuai tema Light dan Dark
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");

        // Membuat judul dan deskripsi form login
        JLabel lbTitle = new JLabel("Welcome Back!");
        JLabel description = new JLabel("let's explore your music world");
        // Styling judul dan deskripsi form
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%)");

        // Styling field username dan password
        usernameTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username or email");
        passwordTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");
        passwordTextField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true"); // Mengaktifkan fitur show/hidden password
        // Styling tombol login
        loginButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        Color originalBackgroundColor = (Color) UIManager.get("Button.background");
        loginButton.putClientProperty("originalBackground", originalBackgroundColor);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover tombol login
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Mouse masuk ke tombol
                loginButton.setBackground(new Color(14, 246, 68));
                loginButton.setForeground(new Color(0, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Mouse keluar dari tombol, kembalikan ke warna awal
                loginButton.setBackground((Color) loginButton.getClientProperty("originalBackground"));
                loginButton.setForeground(new Color(255, 255, 255));
            }
        });

        // Menambahkan komponen ke panel
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Email"), "gapy 8");
        panel.add(usernameTextField);
        panel.add(new JLabel("Password"), "gapy 8");
        panel.add(passwordTextField);
        panel.add(RememberMe, "grow 0");
        panel.add(loginButton, "gapy 10");
        panel.add(createSignupLabel(), "gapy 10");
        add(panel);
    }

    // Membuat tombol register
    private Component createSignupLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Membuat kontainer komponen sign-up
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        JButton registerButton = new JButton("<html><a style='color: #0EF644;' href=\"#\">Sign up</a></html>");
        registerButton.setForeground(new Color(0, 255, 0));

        registerButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:3,3,3,3");
        registerButton.setContentAreaFilled(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(e ->
        {
            FormsManager.getInstance().showForm(new RegistForm());
        });
        JLabel label = new JLabel("Don't have an account ?");
        label.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%)");
        panel.add(label);
        panel.add(registerButton);
        return panel;
    }

    // Metode
    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public void loginUsers(ActionListener actionListener) {
        loginButton.addActionListener(actionListener);
    }

    // Deklarasi
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JCheckBox RememberMe;
    private JButton loginButton;
}
