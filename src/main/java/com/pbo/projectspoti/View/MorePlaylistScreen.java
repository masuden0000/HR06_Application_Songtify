/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.pbo.projectspoti.Model.Playlist;
import com.pbo.projectspoti.View.Component.PlaylistGrid;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author User
 */
public class MorePlaylistScreen extends JScrollPane {
    public MorePlaylistScreen(JPanel mainPanel, DetailScreen detailScreen) {
        this.mainPanel = mainPanel;
        this.detailScreen = detailScreen;
        
        init();
    }
    
    public void init() {
        playlists = HomeScreen.playlists;
        
        layoutPanel = new JPanel();
        layoutPanel.setLayout(new MigLayout("wrap 1", "[fill, 885]", "[20px][5px][80px][]"));

        // Navbar
        navbarPanel = new JPanel(new MigLayout("", "[]push[]"));
        navbarPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");
        BSetting = new JButton("Profile");
        BSetting.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        BSetting.addActionListener(e -> {
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
            MainScreen.backPath = "panelHome";
            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
            FlatAnimatedLafChange.showSnapshot();
            cardLayout.show(mainPanel, "panelHome");
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
        navbarPanel.add(BBack);
        navbarPanel.add(BSetting);

        // Break line (pembatas)
        separator1 = new JSeparator();
        separator1.setForeground(Color.decode("#FFFFFF"));
        
        // Navbar Playlist
        navbarPlaylistPanel = new JPanel(new MigLayout("fillx, wrap", "[]push[]"));
        playlistHeaderLabel = new JLabel("Playlist");
        playlistHeaderLabel.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +3");
        
        navbarPlaylistPanel.add(playlistHeaderLabel, "align left, aligny top");

        // Playlist
        playlistPanel = new JPanel(new MigLayout("align left, wrap 6"));
        plusIcon = new ImageIcon("src\\main\\resources\\icons\\plus-button.png");
        BAddPlaylist = new JButton(plusIcon);
        BAddPlaylist.putClientProperty( "Button.arc", 999 );
        BAddPlaylist.setPreferredSize(new Dimension(50, 50));
        BAddPlaylist.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        BAddPlaylist.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
            FlatAnimatedLafChange.showSnapshot();
            cardLayout.show(mainPanel, "panelAddPlaylist");
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
        playlistPanel.add(BAddPlaylist, "wrap, gapbottom 20");
        for (Playlist playlist : playlists) {
            PlaylistGrid playlistObj = new PlaylistGrid(playlist, mainPanel, detailScreen);
            playlistObj. addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    MainScreen.backPath = "panelMorePlaylist";
                }
            });
            
            playlistPanel.add(playlistObj);
        }
        
        //Menambahkan tiap panel diatas ke panel utama layoutPanel
        layoutPanel.add(navbarPanel, "grow, wrap");
        layoutPanel.add(separator1, "wrap");
        layoutPanel.add(navbarPlaylistPanel, "gaptop 10");
        layoutPanel.add(playlistPanel, "grow, h 150px, wrap");
        
        //Menambahkan layoutPanel ke ScrollPanel
        setViewportView(layoutPanel);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        getVerticalScrollBar().setUnitIncrement(16);
        setBorder(null);
        setPreferredSize(new Dimension(900, 400));
    }
    
    // render ulang ketika ada perubahan data
    public void renderUlang() {
        revalidate();
        repaint();
        init();
    }
    
    private JButton BSetting, BBack, BAddPlaylist;
    private JLabel playlistHeaderLabel;
    private JPanel layoutPanel, navbarPanel, navbarPlaylistPanel, playlistPanel, mainPanel;
    private JSeparator separator1;
    private ImageIcon plusIcon;
    private DetailScreen detailScreen;
    private List<Playlist> playlists;
}
