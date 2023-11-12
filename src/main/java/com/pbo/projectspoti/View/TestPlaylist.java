/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

import com.pbo.projectspoti.Controller.MusicController;
import com.pbo.projectspoti.Model.Playlist;
import com.pbo.projectspoti.Model.Song;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.UIManager;

/**
 *
 * @author User
 */
public class TestPlaylist extends JPanel {
    private MusicController musicController;
    private MusicPlayer musicPlayer;
    private JButton playButton;
    
    public TestPlaylist() {
        musicController = new MusicController();
        
        InitUI(musicController.initSong(), musicController.initPlaylist());
    }
    
    public void InitUI(List<Song> songs,  List<Playlist> playlists) {
        musicPlayer = new MusicPlayer();
        setSize(1200, 500);

        JPanel panelScroll = new JPanel(new GridLayout(songs.size(), 1));
        
        for (Playlist playlist : playlists) {
            JLabel titleLabel = new JLabel(playlist.getTitle());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            
            playButton = new JButton("PLAY");
            playButton.addActionListener(e -> {
                musicController.playPlaylist(musicPlayer, playlist);
            });
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(playButton);
            
            JPanel containerPanel = new JPanel();
            containerPanel.add(titleLabel);
            containerPanel.add(buttonPanel);
            panelScroll.add(containerPanel);
        }
        
        for (Song song : songs) {
            JLabel titleLabel = new JLabel(song.getTitle());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

            JLabel singerLabel = new JLabel(song.getSinger());
            singerLabel.setFont(new Font("Arial", Font.BOLD, 16));
            
            GridBagConstraints gbc = new GridBagConstraints();
            JPanel textPanel = new JPanel(new GridBagLayout());
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.LINE_START;
            textPanel.add(titleLabel, gbc);
            gbc.gridy = 1;
            textPanel.add(singerLabel, gbc);
            
            playButton = new JButton("PLAY");
            playButton.addActionListener(e -> {
                musicController.playSong(song.getSongId(), musicPlayer, songs);
            });
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(playButton);
            
            JPanel containerPanel = new JPanel(new GridLayout(1,3));

            ImageIcon imageIcon = createImageIcon(song.getCover_url());

            if (imageIcon != null) {
                // Tampilkan gambar di dalam JLabel
                JLabel label = new JLabel(imageIcon);
                containerPanel.add(label);
            } else {
                System.out.println("Failed to load image from URL: " + song.getCover_url());
            }
            
            containerPanel.add(textPanel);
            containerPanel.add(buttonPanel);
            
            panelScroll.add(containerPanel);
        }
        
        JScrollPane scrollPane = new JScrollPane(panelScroll);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight()));
        
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

        Dimension dim = new Dimension(20, verticalScrollBar.getPreferredSize().height);
        verticalScrollBar.setPreferredSize(dim);
        UIManager.put("ScrollPane.smoothScrolling", true);

        // Tambahkan JScrollPane ke dalam frame
        setLayout(new BoxLayout (this, BoxLayout.Y_AXIS));
        add(scrollPane);
        add(musicPlayer);
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
