/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;
import com.formdev.flatlaf.FlatClientProperties;
import com.pbo.projectspoti.Controller.MusicController;
import com.pbo.projectspoti.Model.Playlist;
import com.pbo.projectspoti.Model.Song;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.swing.*; 
import javax.swing.border.LineBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author User
 */
public class HomeScreen extends JPanel{
    private JButton usernameButton, logoutButton, tambahPlaylistButton, playButton;
    private Icon plusIcon, iconPlay;
    private JLabel playlistLabel, laguLabel;
    private MusicController musicController;
    private MusicPlayer musicPlayer;
    private JPanel usrAndLgt, listPlaylistPanel, listSongsPanel, addPlaylistPanel, playlist, songs;
    
    public HomeScreen() {
        init();
    }
    
    public static void main(String[] args) {
        HomeScreen homeScreen = new HomeScreen();
    }
    
    private void init() {
        //mengatur layout utama
//        setLayout(new MigLayout("wrap 1", "[grow]", "[]15[]15[]15[]push[]"));
        setLayout(new MigLayout("fill, insets 20", "[center]", "[]15[]15[]15[]push[]"));
        
        //inisialisasi komponen
        musicController = new MusicController();
        usernameButton = new JButton("Username");
        logoutButton = new JButton("Logout");
        playlistLabel = new JLabel("Playlist");
        laguLabel = new JLabel("Lagu");
        listSongsPanel = ListLagu(musicController.initSong());
        usrAndLgt = new JPanel();
        listPlaylistPanel = new JPanel(new MigLayout("fillx"));
        songs = new JPanel();
        
        // button untuk username
        usernameButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#0EF644;"
                + "foreground:#171717;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        
        // button untuk loginButton
        logoutButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#FE4F28;"
                + "foreground:#FBFBFB;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        
        usrAndLgt.setOpaque(false);
        usrAndLgt.setBorder(BorderFactory.createEmptyBorder(20,20,0,40));
        usrAndLgt.add(usernameButton);
        usrAndLgt.add(logoutButton);
        
        // Label playlist
        playlistLabel.putClientProperty(FlatClientProperties.STYLE, ""
        + "foreground: #FBFBFB;"
        + "font:bold +16;");
        
        // kolom untuk menambah playlist
        addPlaylistPanel = new JPanel(new MigLayout("center, insets 50 50 50 50"));
        addPlaylistPanel.putClientProperty(FlatClientProperties.STYLE, ""
        + "background:#4B4B4B;"
        + "foreground:#FBFBFB;"
        + "arc:20;");
        
        listPlaylistPanel.add(addPlaylistPanel);
        
        // icon untuk menambah playlist
        plusIcon = new ImageIcon("src\\main\\resources\\icons\\plus-button.png");
        tambahPlaylistButton = new JButton(plusIcon);
        addPlaylistPanel.add(tambahPlaylistButton);
        
        playlist = new JPanel(new MigLayout("wrap"));
        playlist.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
        playlist.add(playlistLabel, "alignx left");
        playlist.add(addPlaylistPanel, "alignx left");
        
        // Label lagu
        laguLabel.putClientProperty(FlatClientProperties.STYLE, ""
        + "foreground: #FBFBFB;"
        + "font:bold +16;");
        
        songs.setLayout(new MigLayout("wrap"));
        songs.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
        songs.add(laguLabel, "alignx left");
        songs.add(listSongsPanel, "alignx left");
        
        // tambahkan ke frame
        add(usrAndLgt, "wrap, alignx right, aligny top");
        add(playlist, "span 2, aligny top");
        add(songs, "span 2, aligny top");
        add(musicPlayer, "south");
    }
    
    public JPanel ListLagu(List<Song> songs) {
        musicPlayer = new MusicPlayer();
        JPanel FPanel = new JPanel();
        
        JPanel panel = new JPanel(new GridLayout(songs.size(), 1));
        panel.setBackground(new Color(0,0,0));
        
        for (Song song : songs) {
            JLabel titleLabel = new JLabel(song.getTitle());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

            JLabel singerLabel = new JLabel(song.getSinger());
            singerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            
            GridBagConstraints gbc = new GridBagConstraints();
            JPanel textPanel = new JPanel(new GridBagLayout());
            textPanel.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:#4B4B4B;");
            
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.LINE_START;
            textPanel.add(titleLabel, gbc);
            gbc.gridy = 1;
            textPanel.add(singerLabel, gbc);
            
            iconPlay = new ImageIcon("src\\main\\resources\\icons\\play-green.png");
            
            playButton = new JButton(iconPlay);
            playButton.setBorderPainted(false);        
            
            playButton.addActionListener(e -> {
                musicController.playSong(song.getSongId(), musicPlayer, songs);
            });
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:#4B4B4B;");
            buttonPanel.add(playButton);
            
            JPanel containerPanel = new JPanel(new MigLayout("","[][]20[]"));
            containerPanel.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:#4B4B4B;"
            + "arc:20;");
            
            ImageIcon imageIcon = createImageIcon(song.getCover_url());
            
            if (imageIcon != null) {
                // Tampilkan gambar di dalam JLabel
                JLabel label = new JLabel(imageIcon);
                containerPanel.add(label,"spany, growy");
            } else {
                System.out.println("Failed to load image from URL: " + song.getCover_url());
            }
            
            containerPanel.add(buttonPanel);
            containerPanel.add(textPanel);
                    
            panel.add(containerPanel);
        }
        
//        JScrollPane scrollPane = new JScrollPane(FPanel);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight()));
//        
//        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
//
//        Dimension dim = new Dimension(20, verticalScrollBar.getPreferredSize().height);
//        verticalScrollBar.setPreferredSize(dim);
//        UIManager.put("ScrollPane.smoothScrolling", true);
//        
//        setLayout(new BoxLayout (this, BoxLayout.Y_AXIS));
//        panel.add(scrollPane);

        panel.setOpaque(false);
        return panel;
    }
    
    private ImageIcon createImageIcon(String imageUrl) {
        try {
            // Baca gambar dari URL
            URL url = new URL(imageUrl);
            Image originalImage = Toolkit.getDefaultToolkit().getImage(url);

            // Resize gambar
            Image resizedImage = originalImage.getScaledInstance(75, 75, Image.SCALE_SMOOTH);

            // Buat ImageIcon dari gambar yang sudah diresize
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
