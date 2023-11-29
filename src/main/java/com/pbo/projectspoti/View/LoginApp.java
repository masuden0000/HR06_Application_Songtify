package com.pbo.projectspoti.View;

import com.formdev.flatlaf.FlatClientProperties;
import com.pbo.projectspoti.Controller.FormsManager;
import com.pbo.projectspoti.Controller.UserController;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.border.EmptyBorder;

public class LoginApp extends JPanel {

    public LoginApp() {
        userController = new UserController();
        
        init();
    }

    // GUI
    private void init() {
        // Mengatur layout kontainer utama
        setLayout(new MigLayout("fill, insets 0 20 0 0", "[center]", "[center]"));

        // Inisiasi komponen
        usernameTextField = new JTextField();
        passwordTextField = new JPasswordField();
        loginButton = new JButton("Login");

        // Background
        JPanel panel2 = new JPanel(new MigLayout("fill", "[center]", "[center]"));
        JLabel backgroundLabel = new JLabel();

        ImageIcon originalIcon = new ImageIcon("src\\main\\resources\\images\\background2.jpg");
        Image originalImage = originalIcon.getImage();

        // Membuat gambar dengan ukuran yang sama dengan backgroundLabel
        int width = 900;
        int height = 680;
        BufferedImage roundedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Menggambar gambar asli ke gambar dengan sudut yang melengkung (rounded)
        Graphics2D g2d = roundedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Menggambar gambar dengan sudut yang melengkung
        int cornerRadius = 0; // Atur ukuran sudut bulatan
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius);
        g2d.setClip(roundedRectangle);
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();

        // Mengatur gambar yang sudah diubah menjadi ikon pada backgroundLabel
        ImageIcon roundedIcon = new ImageIcon(roundedImage);
        backgroundLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        backgroundLabel.setIcon(roundedIcon);

        // Membuat kontainer untuk form login
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 50", "fill,360:360"));
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
        usernameTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        passwordTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");
        passwordTextField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true"); // Mengaktifkan fitur show/hidden password
        // Styling tombol login
        passwordTextField.setFont(new Font("Monospace", Font.PLAIN, 12));
        loginButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        Color originalBackgroundColor = (Color) UIManager.get("Button.background");
        loginButton.putClientProperty("originalBackground", originalBackgroundColor);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        loginButton.addActionListener(e -> {
            boolean result = userController.login(usernameTextField.getText(), new String(passwordTextField.getPassword()));
            
            if(result) {
                resetForm();
            }
        });

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
        panel.add(new JLabel("Username"), "gapy 8");
        panel.add(usernameTextField);
        panel.add(new JLabel("Password"), "gapy 8");
        panel.add(passwordTextField);
        panel.add(loginButton, "gapy 10");
        panel.add(createSignupLabel(), "gapy 10");
        add(panel);
        add(backgroundLabel);
    }

    // Membuat tombol register
    private Component createSignupLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Membuat kontainer komponen sign-up
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        JButton registerButton = new JButton("<html><a href=\"#\">Sign up</a></html>");
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
    
    public void resetForm() {
        usernameTextField.setText("");
        passwordTextField.setText("");
    }

    // Deklarasi
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private UserController userController;
}
