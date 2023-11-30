/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.pbo.projectspoti.Controller.MusicController;
import com.pbo.projectspoti.Helper.ImageHelper;
import com.pbo.projectspoti.Model.Song;
import com.pbo.projectspoti.View.Component.SongHeaderPanel;
import com.pbo.projectspoti.View.Component.SongList;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author User
 */
public class DetailScreen extends JPanel {
    public DetailScreen(
            JPanel mainPanel, MusicController musicController, MusicPlayer musicPlayer, JLabel detailTitleLabel, 
            JLabel detailSingerLabel, JLabel detailCoverImage, JLabel detailAlbumLabel, JLabel playlistDetailTitleLabel
        ) {
        this.mainPanel = mainPanel;
        this.detailTitleLabel = detailTitleLabel;
        this.detailSingerLabel = detailSingerLabel;
        this.detailCoverImage = detailCoverImage;
        this.detailAlbumLabel = detailAlbumLabel;
        this.musicController = musicController;
        this.musicPlayer = musicPlayer;
        this.playlistDetailTitleLabel = playlistDetailTitleLabel;

        init();
    }
    
    public void init() {
        // panel layout
        layoutPanel = new JPanel(new MigLayout("wrap 1", "[fill]"));
        
        // navbar atas
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
            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
            FlatAnimatedLafChange.showSnapshot();
            cardLayout.show(mainPanel, MainScreen.backPath);
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
        navbarPanel.add(BBack);
        navbarPanel.add(BSetting);
        
        // Break line (pembatas)
        separator1 = new JSeparator();
        separator1.setForeground(Color.decode("#FFFFFF"));
        
        // Informasi
        infoPanel = new JPanel(new MigLayout("align left"));
        infoTextPanel = new JPanel(new MigLayout("wrap 1"));
        
        coverIcon = ImageHelper.createImageFromURL(MainScreen.playlistData.getCoverUrl(), 200, 200, true);
        coverImage = new JLabel();
        coverImage.setIcon(coverIcon);
        
        playlistTypeLabel = new JLabel("Public Playlist");
        playlistTypeLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        
        playlistTitleLabel = new JLabel(MainScreen.playlistData.getTitle());
        playlistTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 34));
        
        playlistAuthorLabel = new JLabel(MainScreen.playlistData.getAuthor());
        playlistAuthorLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        
        descriptionLabel = new JTextArea(MainScreen.playlistData.getDescription());
        descriptionLabel.setOpaque(false);
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setWrapStyleWord(true);
        descriptionLabel.setEditable(false);
        descriptionLabel.setPreferredSize(new Dimension(500, 80));
        descriptionLabel.setFont(new Font("Roboto", Font.PLAIN, 12));
        
        BDelPlaylist = new JButton("Hapus");
        BDelPlaylist.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        BDelPlaylist.setBackground(new Color(198,0,0));
        BDelPlaylist.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus?", "Konfirmasi hapus", JOptionPane.OK_CANCEL_OPTION);
                
            if (result == JOptionPane.YES_OPTION) {
                musicController.deletePlaylist(MainScreen.playlistData.getPlaylistId());
                FlatAnimatedLafChange.showSnapshot();
                homeScreen.renderUlang();
                morePlaylistScreen.renderUlang();
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "panelHome");
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
            }
        });
        
        infoTextPanel.add(playlistTypeLabel);
        infoTextPanel.add(playlistTitleLabel);
        infoTextPanel.add(playlistAuthorLabel);
        if(MainScreen.playlistData.getAuthor().equals(MainFrame.loggedUser.getUsername())) {
            infoTextPanel.add(BDelPlaylist);
        }
        infoTextPanel.add(descriptionLabel);
        infoPanel.add(coverImage);
        infoPanel.add(infoTextPanel);
        
        // Button Panel
        buttonPanel = new JPanel(new MigLayout());
        playIcon = new ImageIcon("src\\main\\resources\\icons\\play-green.png");
        BPlay = new JButton(playIcon);
        BPlay.setPreferredSize(new Dimension(50, 50));
        BPlay.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "arc:999"
        );
        BPlay.addActionListener(e -> {
            musicController.playPlaylist(musicPlayer, MainScreen.playlistData);
            playlistDetailTitleLabel.setText(MainScreen.playlistData.getTitle());
        });
        
        if (MainScreen.playlistData.getPlayList().size() != 0) {
            buttonPanel.add(BPlay);
        }
                
        // lagu
        songPanel = new JPanel(new GridLayout(MainScreen.playlistData.getPlayList().size(), 1));
        
        songHeaderPanel = new SongHeaderPanel();
        
        for(Song song : MainScreen.playlistData.getPlayList()) {
            SongList songList = new SongList(
                    song, detailTitleLabel, detailSingerLabel, detailCoverImage,  
                    detailAlbumLabel,  true, MainScreen.playlistData.getAuthor()
            );
            songList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    musicController.playSong(song.getSongId(), musicPlayer, MainScreen.playlistData.getPlayList());
                    playlistDetailTitleLabel.setText(MainScreen.playlistData.getTitle());

                }
            });
            songList.playSong(e -> {
                musicController.playSong(song.getSongId(), musicPlayer, MainScreen.playlistData.getPlayList());
                playlistDetailTitleLabel.setText(MainScreen.playlistData.getTitle());
            });
            songList.deleteFromPlaylist(e -> {
                int result = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus?", "Konfirmasi hapus", JOptionPane.OK_CANCEL_OPTION);
                
                if (result == JOptionPane.YES_OPTION) {
                    musicController.deleteFromPlaylist(song.getSongId(), MainScreen.playlistData.getPlaylistId());
                    FlatAnimatedLafChange.showSnapshot();
                    homeScreen.renderUlang();
                    morePlaylistScreen.renderUlang();
                    CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                    cardLayout.show(mainPanel, "panelHome");
                    FlatAnimatedLafChange.hideSnapshotWithAnimation();
                }
            });
            songPanel.add(songList);
        }
        songPanel.setPreferredSize(new Dimension(880, HEIGHT));
        
        // tambahin semua ke panel layout
        layoutPanel.add(navbarPanel, "grow, wrap");
        layoutPanel.add(separator1, "wrap");
        layoutPanel.add(infoPanel);
        layoutPanel.add(buttonPanel, "gaptop 10, gapbottom 10");
        layoutPanel.add(songHeaderPanel);
        layoutPanel.add(songPanel);
        
        // tambah panel ke scroll pane
        scrollPane = new JScrollPane(layoutPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(900, 580));

        add(scrollPane);
    }
    
    // render ulang ketika ada perubahan data
    public void renderUlang() {
        removeAll();
        revalidate();
        repaint();
        init();
    }
    
    // method setter
    public void setHomeScreen(HomeScreen homeScreen) {
        this.homeScreen = homeScreen;
    }
    public void setMorePlaylistScreen(MorePlaylistScreen morePlaylistScreen) {
        this.morePlaylistScreen = morePlaylistScreen;
    }
    
    private JPanel infoPanel, infoTextPanel, buttonPanel, songPanel, songHeaderPanel, layoutPanel, mainPanel, navbarPanel;
    private JLabel coverImage, detailTitleLabel, detailSingerLabel, detailCoverImage, detailAlbumLabel, playlistDetailTitleLabel;
    private JTextArea descriptionLabel;
    private JLabel playlistTypeLabel, playlistTitleLabel, playlistAuthorLabel;
    private JButton BSetting, BBack, BPlay, BDelPlaylist;
    private ImageIcon coverIcon, playIcon;
    private JScrollPane  scrollPane;
    private MusicController musicController;
    private MusicPlayer musicPlayer;
    private JSeparator separator1;
    private HomeScreen homeScreen;
    private MorePlaylistScreen morePlaylistScreen;
}
