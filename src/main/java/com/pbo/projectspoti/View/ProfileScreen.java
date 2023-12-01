/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.pbo.projectspoti.Controller.FormsManager;
import com.pbo.projectspoti.Helper.EncryptHelper;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author User
 */
public class ProfileScreen extends JPanel {
    public ProfileScreen(JPanel mainPanel) {
        outerPanel = new JPanel();
        layoutPanel = new JPanel(new MigLayout("align center" ));
        layoutPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;");
        
        setLayout(new MigLayout("wrap 1, align center", "[fill]"));
        
         // navbar atas
        navbarPanel = new JPanel(new MigLayout("", "[]push[]"));
        navbarPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");
        BProfile = new JButton("Profile");
        BProfile.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        BBack = new JButton("Back");
        BBack.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        BBack.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
            FlatAnimatedLafChange.showSnapshot();
            cardLayout.show(mainPanel, "panelHome");
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
        navbarPanel.add(BBack);
        navbarPanel.add(BProfile);
        
        // Break line (pembatas)
        separator1 = new JSeparator();
        separator1.setForeground(Color.decode("#FFFFFF"));
        
        avatarIcon = new ImageIcon("src\\main\\resources\\icons\\chicken.png");
        avatarLabel = new JLabel(avatarIcon);
        avatarLabel.setForeground(Color.WHITE);
        
        usernameLabel = new JLabel("Username: " + MainFrame.loggedUser.getUsername());
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        
        fullnameLabel = new JLabel("Fullname : " + MainFrame.loggedUser.getFullName());
        fullnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        
        BLogout = new JButton("Logout");
        BLogout.setPreferredSize(new Dimension(50, 25));
        BLogout.setBackground(new Color(198,0,0));
        BLogout.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "font: bold 14 Roboto");
        BLogout.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin keluar?", "Konfirmasi keluar", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                MusicPlayer.getPlayer().close();
                FlatAnimatedLafChange.showSnapshot();
                EncryptHelper.deleteLogin("user.txt");
                FormsManager.getInstance().showForm(new LoginApp());
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
            }
        });
        
        layoutPanel.add(avatarLabel, "wrap");
        layoutPanel.add(usernameLabel, "wrap, gaptop 10");
        layoutPanel.add(fullnameLabel, "wrap, gaptop 10");
        layoutPanel.add(BLogout, "gaptop 25");
        layoutPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        
        outerPanel.add(layoutPanel);
        outerPanel.setPreferredSize(new Dimension(890, 500));
        
        add(navbarPanel);
        add(separator1);
        add(outerPanel, "align right, gaptop 10");
    }
    
    private JPanel layoutPanel, navbarPanel, outerPanel;
    private JLabel usernameLabel, fullnameLabel, avatarLabel;
    private ImageIcon avatarIcon;  
    final private JSeparator separator1;
    final private JButton BLogout, BBack, BProfile;
}
