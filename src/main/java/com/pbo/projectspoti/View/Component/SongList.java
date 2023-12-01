/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View.Component;

import com.formdev.flatlaf.FlatClientProperties;
import com.pbo.projectspoti.Helper.ImageHelper;
import com.pbo.projectspoti.Helper.StringHelper;
import com.pbo.projectspoti.Model.Song;
import com.pbo.projectspoti.View.MainFrame;
import com.pbo.projectspoti.View.MainScreen;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author User
 */
public class SongList extends JPanel{
    public SongList(
            Song song, JLabel detailTitleLabel, 
            JLabel detailSingerLabel, JLabel detailCoverImage,  
            JLabel detailAlbumLabel, boolean isInPlaylist, 
            String author
    ) {        
        // Setup Layout
        setLayout(new MigLayout("align left"));
        
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        
        //Labels
        songTitleLabel = new JLabel(song.getTitle());
        songTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 15));

        songSingerLabel = new JLabel(song.getSinger());
        songSingerLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        
        songAlbumLabel = new JLabel(StringHelper.truncateString(song.getAlbum(), 17));
        songAlbumLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        songDurationLabel = new JLabel(song.getDuration());
        songDurationLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        //Button
        playIcon = new ImageIcon("src\\main\\resources\\icons\\play-green.png");
        Image image = playIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        playIcon = new ImageIcon(image);
        pauseIcon = new ImageIcon("src\\main\\resources\\icons\\pause-green.png");
        image = pauseIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        pauseIcon = new ImageIcon(image);
        BPlaySong = new JButton(playIcon);
        BPlaySong.setPreferredSize(new Dimension(30, 30));
        BPlaySong.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");

        BPlaySong.addActionListener(e -> {
                if (MainScreen.isPlayButton != null) {
                   // Reset the background color of the previous selected panel
                   MainScreen.isPlayButton.setIcon(playIcon);
                }
                MainScreen.isPlayButton = BPlaySong;
                BPlaySong.setIcon(pauseIcon);
        });
        
        addPlaylistIcon = new ImageIcon("src\\main\\resources\\icons\\add-list.png");
        image = addPlaylistIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        addPlaylistIcon = new ImageIcon(image);
        BAddToPlaylist = new JButton(addPlaylistIcon);
        BAddToPlaylist.setFont(new Font("Tahoma", Font.PLAIN, 18));
        BAddToPlaylist.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        
        BDelSong = new JButton("x");
        BDelSong.setFont(new Font("Tahoma", Font.BOLD, 18));
        BDelSong.setPreferredSize(new Dimension(35, 35));
        BDelSong.setBackground(new Color(198,0,0));
        BDelSong.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        
        //Panel untuk tiap label
        songTitlePanel = new JPanel(new MigLayout());
        songTitlePanel.setPreferredSize(new Dimension(200, 100));
        songTitlePanel.add(songTitleLabel, "wrap");
        songTitlePanel.add(songSingerLabel, "wrap");
        songTitlePanel.setOpaque(false);
        
        songAlbumPanel = new JPanel(new GridBagLayout());
        songAlbumPanel.setPreferredSize(new Dimension(150, 100));
        songAlbumPanel.add(songAlbumLabel, gbc);
        songAlbumPanel.setOpaque(false);
        
        songDurationPanel = new JPanel(new GridBagLayout());
        songDurationPanel.setPreferredSize(new Dimension(80, 100));
        songDurationPanel.add(songDurationLabel, gbc);
        songDurationPanel.setOpaque(false);

        songBPlayPanel = new JPanel(new GridBagLayout());
        songBPlayPanel.setPreferredSize(new Dimension(80, 200));
        songBPlayPanel.setOpaque(false);
        songBPlayPanel.add(BPlaySong);
        
        songBAddToPanel = new JPanel(new GridBagLayout());
        songBAddToPanel.setPreferredSize(new Dimension(80, 200));
        songBAddToPanel.setOpaque(false);
        if(!isInPlaylist) {
            songBAddToPanel.add(BAddToPlaylist);
        } else {
            if(MainFrame.loggedUser.getUsername().equals(author)) {
                songBAddToPanel.add(BDelSong);
            }
        }
        
        // Cover Icon
        coverImage = new JLabel();
        ImageHelper.loadAsyncImage(song.getCover_url(), coverImage, 50, 50, true);
        add(coverImage,"spany, growy");
        
        // Menambahkan seluruh panel ke parent
        add(songTitlePanel);
        add(songAlbumPanel);
        add(songDurationPanel);
        add(songBPlayPanel);
        add(songBAddToPanel, "gapleft 20, align right");
               
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Panel dihover, ganti warna
                setBackground(new Color(50, 50, 50));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(null);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (MainScreen.isPlayButton != null) {
                    // Reset the background color of the previous selected panel
                    MainScreen.isPlayButton.setIcon(playIcon);
                }
                MainScreen.isPlayButton = BPlaySong;
                BPlaySong.setIcon(pauseIcon);
            }
          });
        setPreferredSize(new Dimension(350, 80));
    }
    
    public JButton getBPlaySong() {
        return BPlaySong;
    }
    
    public void addToPlaylist(ActionListener actionListener) {
        BAddToPlaylist.addActionListener(actionListener);
    }
    
    public void deleteFromPlaylist(ActionListener actionListener) {
        BDelSong.addActionListener(actionListener);
    }
    
    public void playSong(ActionListener actionListener) {
        BPlaySong.addActionListener(actionListener);
    }
        
    final private GridBagConstraints gbc;
    final private JLabel songTitleLabel, songSingerLabel, songAlbumLabel, songDurationLabel;
    final private JLabel coverImage;
    final private JPanel songTitlePanel, songAlbumPanel, songDurationPanel, songBPlayPanel, songBAddToPanel;
    final private JButton BPlaySong, BAddToPlaylist, BDelSong;
    private ImageIcon playIcon, pauseIcon, addPlaylistIcon, detailCoverIcon; 
}
