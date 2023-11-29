/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

import com.pbo.projectspoti.Controller.MusicController;
import com.pbo.projectspoti.Model.Playlist;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author User
 */
public class MainScreen extends JPanel {
    public MainScreen() {
        // init controller music
        musicController = new MusicController();
        infoPanel = new InfoPanel();
        
        // init playlist data untuk halaman detail
        playlistData = new Playlist("", "", "", "", "", new ArrayList<>());
        
        // init global label
        detailTitleLabel = new JLabel("Please play any song!");
        detailTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        detailSingerLabel = new JLabel();
        detailSingerLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        detailAlbumLabel = new JLabel();
        detailAlbumLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        playlistLabel = new JLabel("Playlist:");
        playlistLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        playlistTitleLabel = new JLabel("All Song");
        playlistTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        nextLabel = new JLabel("Next:");
        nextLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        nextLabel.setForeground(Color.GREEN);
        nextSongLabel = new JLabel("");
        nextSongLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        prevLabel = new JLabel("Prev:");
        prevLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        prevLabel.setForeground(Color.RED);
        prevSongLabel = new JLabel("");
        prevSongLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        detailCoverImage = new JLabel();
        noResulIcon = new ImageIcon("src\\main\\resources\\icons\\no-results.png");
        Image image = noResulIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        noResulIcon = new ImageIcon(newimg);
        detailCoverImage.setIcon(noResulIcon);
        coverDetailPanel = new JPanel(new GridBagLayout());
        coverDetailPanel.add(detailCoverImage);
        coverDetailPanel.setOpaque(false);
        coverDetailPanel.setPreferredSize(new Dimension(200, 200));
        
        init();
    }

    private void init() {
        //Panel Utama yg paling besar terdiri dari detail, home, playlistForm, dan morePlaylist screen
        mainPanel = new JPanel(new CardLayout());
        musicPlayer = new MusicPlayer(detailTitleLabel, detailSingerLabel, detailCoverImage, detailAlbumLabel, nextSongLabel, prevSongLabel);
        detailScreen =  new DetailScreen(mainPanel, musicController, musicPlayer, detailTitleLabel, detailSingerLabel, detailCoverImage, detailAlbumLabel, playlistTitleLabel);
        homeScreen = new HomeScreen(mainPanel, musicController, musicPlayer, detailScreen, detailTitleLabel, detailSingerLabel, detailCoverImage, detailAlbumLabel, playlistTitleLabel);
        detailScreen.setHomeScreen(homeScreen);
        morePlaylistScreen = new MorePlaylistScreen(mainPanel, detailScreen);
        detailScreen.setMorePlaylistScreen(morePlaylistScreen);
        homeScreen.setMorePlaylistScreen(morePlaylistScreen);
        playlistForm = new PlaylistForm(mainPanel, musicController, homeScreen, morePlaylistScreen);
        profileScreen = new ProfileScreen(mainPanel);
        
        mainPanel.add(homeScreen, "panelHome");
        mainPanel.add(detailScreen, "panelDetail");
        mainPanel.add(playlistForm, "panelAddPlaylist");
        mainPanel.add(profileScreen, "panelProfile");
        mainPanel.add(morePlaylistScreen, "panelMorePlaylist");
        
        //Info Panel (Kanan)
        infoPanel.add(detailTitleLabel, "gaptop 10, align left");
        infoPanel.add(detailSingerLabel, "align left");
        infoPanel.add(detailAlbumLabel, "align left");
        infoPanel.add(coverDetailPanel, "gaptop 10, align center");
        
        playlistPanel = new JPanel(new MigLayout("wrap 1"));
        playlistPanel.add(playlistLabel, "gaptop 5, align left");
        playlistPanel.add(playlistTitleLabel, "align left");
        playlistPanel.setOpaque(false);
        infoPanel.add(playlistPanel);
        
        nextPanel = new JPanel(new MigLayout("wrap 1"));
        nextPanel.add(nextLabel, "gaptop 5, align left");
        nextPanel.add(nextSongLabel, "align left");
        nextPanel.setOpaque(false);
        infoPanel.add(nextPanel);
        
        prevPanel = new JPanel(new MigLayout("wrap 1"));
        prevPanel.add(prevLabel, "gaptop 5, align left");
        prevPanel.add(prevSongLabel, "align left");
        prevPanel.setOpaque(false);
        infoPanel.add(prevPanel);
        
        //Music Player (Bawah)
        musicPanel = new JPanel();
        musicPanel.add(musicPlayer);
        musicPlayer.setPreferredSize(new Dimension(1200,  120));
        musicPanel.setBackground(new Color(40,40,40));
        musicPanel.setPreferredSize(new Dimension(1200, 120));
        
        //Menggabungkan seluruh panel
        topPanel = new JPanel(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(1200, 580));
        topPanel.add(mainPanel);
        topPanel.add(infoPanel);
        add(topPanel);
        add(musicPanel);
    }
    
    public static JButton isPlayButton = null;
    public static String backPath = "panelHome";
    public static Playlist playlistData;
        
    private MusicController musicController;
    private MusicPlayer musicPlayer;
    private InfoPanel infoPanel;
    private HomeScreen homeScreen;
    private PlaylistForm playlistForm;
    private ProfileScreen profileScreen;
    private MorePlaylistScreen morePlaylistScreen;
    
    private ImageIcon noResulIcon;
    final private JLabel detailTitleLabel, 
            detailSingerLabel, 
            detailCoverImage, detailAlbumLabel, 
            nextLabel, nextSongLabel, prevLabel, 
            prevSongLabel, playlistLabel, 
            playlistTitleLabel; 
    private JPanel musicPanel, topPanel, mainPanel, nextPanel, prevPanel, playlistPanel, coverDetailPanel;
    private DetailScreen detailScreen;
}
