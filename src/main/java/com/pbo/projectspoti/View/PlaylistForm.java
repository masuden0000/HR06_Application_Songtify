/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

import com.pbo.projectspoti.Helper.ImageHelper;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.pbo.projectspoti.Controller.MusicController;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author User
 */
public class PlaylistForm extends JPanel {
    public PlaylistForm(JPanel mainPanel, MusicController musicController, HomeScreen homeScreen, MorePlaylistScreen morePlaylistScreen) {
        outerPanel = new JPanel();
        layoutPanel = new JPanel(new MigLayout("insets 35 45 30 45" ));
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
        BProfile.addActionListener(e -> {
            FlatAnimatedLafChange.showSnapshot();
            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
            cardLayout.show(mainPanel, "panelProfile");
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
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
        
        // Header
        headerLabel = new JLabel("Add Playlist");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Roboto", Font.BOLD, 25));
        
        // Pilih cover (kiri)
        coverPanel = new JPanel(new MigLayout("wrap 1", "", "[]push[]"));
        selectCoverLabel = new JLabel("Select your playlist cover!");
        selectCoverLabel.setFont(new Font("Open Sans", Font.PLAIN, 14));
        coverSelectionPanel = new JPanel(new MigLayout("wrap"));
        coverSelectionPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:100;");
        coverSelectionPanel.setOpaque(false);
        
        // input (kanan)
        inputPanel = new JPanel(new MigLayout("wrap"));
        
        titleLabel = new JLabel("Add playlist title");
        
        titlePlaylistField = new JTextField();
        titlePlaylistField.setPreferredSize(new Dimension(600, titlePlaylistField.getPreferredSize().height));
        titlePlaylistField.putClientProperty(FlatClientProperties.STYLE, ""
            + "font: 18 Roboto;"
            + "focusColor:#FFFFFF;"
            +"margin:10, 20, 10, 20"); 
        
        descriptionLabel = new JLabel("Add description");
        
        descPlaylistField = new JTextArea();
        descPlaylistField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter playlist description!");
        descPlaylistField.setPreferredSize(new Dimension(600, 150));
        descPlaylistField.setLineWrap(true);
        descPlaylistField.setWrapStyleWord(true);
        descPlaylistField.putClientProperty(FlatClientProperties.STYLE, ""
            + "font:14 Roboto;"
            + "margin:10, 20, 10, 20;"); 
        
        inputPanel.setOpaque(false);
        inputPanel.add(titleLabel);
        inputPanel.add(titlePlaylistField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descPlaylistField);
        
        for (String coverUrl : coverUrlList) {
            JPanel coverPanel = new JPanel(new GridBagLayout());
            JLabel coverImage = new JLabel();
            ImageHelper.loadAsyncImage(coverUrl, coverImage, 120, 120, true);
            coverPanel.add(coverImage);
            coverPanel.setPreferredSize(new Dimension(150, 150));
            coverPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedPanel != null) {
                            // Reset the background color of the previous selected panel
                            selectedPanel.setBackground(null);
                        }
                    selectedPanel = coverPanel;
                    coverPanel.setBackground(new Color(85, 83, 85));
                    coverPlaylistField = coverUrl;
                }
            });
            coverSelectionPanel.add(coverPanel);
        }
        
        scrollPane = new JScrollPane(coverSelectionPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(200, 225));
        
        coverPanel.add(selectCoverLabel);
        coverPanel.add(scrollPane, "gaptop 5");
        coverPanel.setOpaque(false);
        
        BSubmit = new JButton("Submit");
        BSubmit.setPreferredSize(new Dimension(50, 25));
        BSubmit.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#FFFFFF;"
                + "foreground:#000000;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "font: bold 14 Roboto");
        BSubmit.addActionListener(e -> {
            boolean result = musicController.addPlaylist(titlePlaylistField.getText(), descPlaylistField.getText(), coverPlaylistField, MainFrame.loggedUser.getUserId());
            
            if (result) {
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                FlatAnimatedLafChange.showSnapshot();
                cardLayout.show(mainPanel, "panelHome");
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
                homeScreen.renderUlang();
                morePlaylistScreen.renderUlang();
                resetForm();
            }
        });
        
        layoutPanel.add(headerLabel, "wrap");
        layoutPanel.add(coverPanel);
        layoutPanel.add(inputPanel, "wrap");
        layoutPanel.add(BSubmit, "gaptop 25");
        
        outerPanel.add(layoutPanel);
        outerPanel.setPreferredSize(new Dimension(900, 500));
        
        add(navbarPanel);
        add(separator1);
        add(outerPanel, "align right, gaptop 10");
    }
    
    // reset form saat klik submit
    private void resetForm() {
        titlePlaylistField.setText("");
        descPlaylistField.setText("");
        coverPlaylistField = "";
        selectedPanel.setBackground(null);
        selectedPanel = null;
    }
    
    private static JPanel selectedPanel = null;
    
    private JScrollPane scrollPane;
    private JPanel coverSelectionPanel, layoutPanel, inputPanel, navbarPanel, outerPanel, coverPanel;
    private JLabel selectCoverLabel, descriptionLabel, headerLabel, titleLabel;
    private JTextField titlePlaylistField;
    private JTextArea descPlaylistField;
    private JSeparator separator1;
    private String coverPlaylistField = "";
    private JButton BSubmit, BBack, BProfile;
    private String[] coverUrlList = {"https://i.pinimg.com/564x/57/2b/fa/572bfadc9b5c5fc6658172f4202ff780.jpg",
        "https://i.pinimg.com/564x/84/d3/74/84d374f8af97c6ba0f9ef20bd53b5f01.jpg",
        "https://i.pinimg.com/564x/00/cb/5d/00cb5dd2a1b3079cd192ae58e17f249f.jpg",
        "https://i.pinimg.com/564x/ee/ab/c8/eeabc8f218501511e52bb049db63d051.jpg",
        "https://i.pinimg.com/564x/96/45/4c/96454cd761657bb7b258e73cdc3146e0.jpg",
        "https://i.pinimg.com/564x/6e/d9/85/6ed985ba345a5189e8e9ba3fbcaefb1f.jpg",
        "https://i.pinimg.com/564x/93/3f/71/933f71ff8d02c04e1cab2a00e6901187.jpg",
        "https://i.pinimg.com/564x/02/87/04/028704fb8743113ed624b7f390c8f858.jpg"
    };
}
