package com.pbo.projectspoti.View;

import com.formdev.flatlaf.FlatClientProperties;
import com.pbo.projectspoti.Controller.FormsManager;
import com.pbo.projectspoti.Controller.UserController;
import net.miginfocom.swing.MigLayout;
import com.pbo.projectspoti.View.Component.PasswordStrengthStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.border.EmptyBorder;

public class RegistForm extends JPanel {

    public static boolean isRegistered = false;

    public RegistForm() {
        userController = new UserController();
        
        init();
    }

    // GUI
    private void init() {
        // Mengatur layout kontainer utama
        setLayout(new MigLayout("fill, insets 0 20 0 0", "[center]", "[center]"));

        // Inisiasi komponen
        fullnameTextField = new JTextField();
        usernameTextField = new JTextField();
        passwordTextField = new JPasswordField();
        confirmPassword = new JPasswordField();
        registerButton = new JButton("Sign Up");
        passwordStrengthStatus = new PasswordStrengthStatus();

        // Background
        JPanel panel2 = new JPanel(new MigLayout("fill", "[center]", "[center]"));
        JLabel backgroundLabel = new JLabel();

        ImageIcon originalIcon = new ImageIcon("src\\main\\resources\\images\\background1.jpg");
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

        // Membuat kontainer untuk form register
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 80", "[fill,360]"));
        // Memberikan warna panel lebih terang dibanding background-nya sesuai tema Light dan Dark
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");

        // Membuat judul dan deksripsi form register
        JLabel lbTitle = new JLabel("Welcome to Songtify");
        JLabel description = new JLabel("Sign up and enjoy a variety of your favorite music playlists");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%)");

        // Styling field 
        fullnameTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "First name");
        usernameTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        passwordTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");
        confirmPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Re-enter your password");
        passwordTextField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true");
        passwordTextField.setFont(new Font("Monospace", Font.PLAIN, 12));
        confirmPassword.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true");
        confirmPassword.setFont(new Font("Monospace", Font.PLAIN, 12));
        // Styling tombol register
        registerButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        Color originalBackgroundColor = (Color) UIManager.get("Button.background");
        registerButton.putClientProperty("originalBackground", originalBackgroundColor);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Memanggil method untuk mengecek kekuatan password
        passwordStrengthStatus.initPasswordField(passwordTextField);

        // Hover tombol register
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Mouse masuk ke tombol
                registerButton.setBackground(new Color(14, 246, 68));
                registerButton.setForeground(new Color(0, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Mouse keluar dari tombol, kembalikan ke warna awal
                registerButton.setBackground((Color) registerButton.getClientProperty("originalBackground"));
                registerButton.setForeground(new Color(255, 255, 255));
            }
        });

        // Pop-up
        JFrame frame = new JFrame("Form Example");

        // Reset field
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = userController.register(
                        usernameTextField.getText(), 
                        fullnameTextField.getText(), 
                        new String(passwordTextField.getPassword()), 
                        new String(confirmPassword.getPassword())
                );
                
                if (result) {
                    reset();
                }
            }
        });

        // Menambahkan komponen ke panel
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Full Name"), "gapy 10");
        panel.add(fullnameTextField);
        panel.add(new JLabel("Username"));
        panel.add(usernameTextField);
        panel.add(new JLabel("Password"), "gapy 8");
        panel.add(passwordTextField);
        panel.add(passwordStrengthStatus, "gapy 0");
        panel.add(new JLabel("Confirm Password"), "gapy 0");
        panel.add(confirmPassword);
        panel.add(registerButton, "gapy 20");
        panel.add(createLoginLabel(), "gapy 10");
        add(panel);
        add(backgroundLabel);
    }

    // Membuat tombol login
    private Component createLoginLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        JButton cmdLogin = new JButton("<html><a href=\"#\">Sign in here</a></html>");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:3,3,3,3");
        cmdLogin.setContentAreaFilled(false);
        cmdLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdLogin.addActionListener(e ->
        {
            FormsManager.getInstance().showForm(new LoginApp());
        });
        JLabel label = new JLabel("Already have an account ?");
        label.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%)");
        panel.add(label);
        panel.add(cmdLogin);
        return panel;
    }

    // Reset fields
    public void reset() {
        fullnameTextField.setText("");
        usernameTextField.setText("");
        passwordTextField.setText("");
        confirmPassword.setText("");
    }

    //Metode
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

    // Deklarasi
    private JTextField fullnameTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JPasswordField confirmPassword;
    private JButton registerButton;
    private PasswordStrengthStatus passwordStrengthStatus;
    private Icon checklist;
    final private UserController userController;
}
