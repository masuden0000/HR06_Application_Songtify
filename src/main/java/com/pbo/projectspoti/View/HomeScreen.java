/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.pbo.projectspoti.Controller.MusicController;
import com.pbo.projectspoti.Helper.ImageHelper;
import com.pbo.projectspoti.Helper.StringHelper;
import com.pbo.projectspoti.Model.Playlist;
import com.pbo.projectspoti.Model.Song;
import com.pbo.projectspoti.View.Component.PlaylistGrid;
import com.pbo.projectspoti.View.Component.SongHeaderPanel;
import com.pbo.projectspoti.View.Component.SongList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author User
 */
public class HomeScreen extends JScrollPane{
    public HomeScreen(
            JPanel mainPanel, MusicController musicController,MusicPlayer musicPlayer, DetailScreen detailScreen, 
            JLabel detailTitleLabel, JLabel detailSingerLabel, JLabel detailCoverImage, JLabel detailAlbumLabel, JLabel playlistTitleLabel
    ) {
        this.musicController = musicController;
        this.detailScreen = detailScreen;
        this.musicPlayer = musicPlayer;
        this.mainPanel = mainPanel;
        this.detailTitleLabel = detailTitleLabel;
        this.detailSingerLabel = detailSingerLabel;
        this.detailCoverImage = detailCoverImage;
        this.detailAlbumLabel = detailAlbumLabel;
        this.playlistTitleLabel = playlistTitleLabel;
        
        init();
    }
    
    private void init() {
        counter = 0;
        songs = musicController.initSong();
        playlists = musicController.initPlaylist();
        
        layoutPanel = new JPanel();
        layoutPanel.setLayout(new MigLayout("wrap 1, fill", "[fill]", "[20px][5px][30px][200px][30px][20px][5px][150px]"));

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
        
        BRefreshPage = new JButton("Refresh");
        BRefreshPage.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        BRefreshPage.addActionListener(e -> {
            renderUlang();
            morePlaylistScreen.renderUlang();
        });
        navbarPanel.add(BRefreshPage);
        navbarPanel.add(BSetting);

        // Break line (pembatas)
        separator1 = new JSeparator();
        separator1.setForeground(Color.decode("#FFFFFF"));
        
        // Navbar Playlist
        navbarPlaylistPanel = new JPanel(new MigLayout("fillx, wrap", "[]push[]"));
        playlistHeaderLabel = new JLabel("Playlist");
        playlistHeaderLabel.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +3");
        BLoadMorePlaylist = new JButton("Load more");
        BLoadMorePlaylist.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        BLoadMorePlaylist.addActionListener(e -> {
                FlatAnimatedLafChange.showSnapshot();
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "panelMorePlaylist");
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
        
        navbarPlaylistPanel.add(playlistHeaderLabel, "align left, aligny top");
        navbarPlaylistPanel.add(BLoadMorePlaylist, "align right, aligny top");

        // Playlist
        playlistPanel = new JPanel(new MigLayout("align left"));
        for (Playlist playlist : playlists) {
            if (counter < 5) {
                counter++;
                playlistPanel.add(new PlaylistGrid(playlist, mainPanel, detailScreen));
            }
        }
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
        playlistPanel.add(BAddPlaylist, "gapleft 10");

        // Navbar Lagu
        navbarSongPanel = new JPanel(new MigLayout("fillx, wrap", "[]push[]"));
        songHeaderLabel = new JLabel("Song");
        songHeaderLabel.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +3");
        navbarSongPanel.add(songHeaderLabel, "alignx right");
       
        // Lagu
        songPanel = new JPanel(new GridLayout(songs.size(), 1));
        songHeaderPanel = new SongHeaderPanel();
        for (Song song : songs) {
            SongList songList = new SongList(
                    song, detailTitleLabel, detailSingerLabel, detailCoverImage, 
                    detailAlbumLabel, false, ""
            );
            songList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    musicController.playSong(song.getSongId(), musicPlayer, songs);
                    playlistTitleLabel.setText("All Song");
                }
            });
            songList.addToPlaylist(e -> {
                showAddToPlaylistDialog(songList, song.getSongId());
            });
            songList.playSong(e -> {
                musicController.playSong(song.getSongId(), musicPlayer, songs);
                playlistTitleLabel.setText("All Song");
            });
            songPanel.add(songList);
        }
        
        // Break line (pembatas)
        separator2 = new JSeparator();
        separator2.setForeground(Color.decode("#FFFFFF"));
        
        //Menambahkan tiap panel diatas ke panel utama layoutPanel
        layoutPanel.add(navbarPanel, "grow, wrap");
        layoutPanel.add(separator1, "wrap");
        layoutPanel.add(navbarPlaylistPanel, "gaptop 10");
        layoutPanel.add(playlistPanel, "grow, h 150px, wrap");
        layoutPanel.add(navbarSongPanel, "gaptop 10");
        layoutPanel.add(songHeaderPanel);
        layoutPanel.add(separator2, "wrap");
        layoutPanel.add(songPanel);
        
        //Menambahkan layoutPanel ke ScrollPanel
        setViewportView(layoutPanel);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        getVerticalScrollBar().setUnitIncrement(16);
        setBorder(null);
        setPreferredSize(new Dimension(900, 400));
    }
    
    // Jdialog untuk menambah ke album
    private void showAddToPlaylistDialog(JPanel owner, String songid) {
        JDialog playlistDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(owner), "Add to playlist", true);
        
        JPanel layoutPanel = new JPanel(new MigLayout("wrap"));
        JPanel addToListPanel = new JPanel(new MigLayout("wrap 2, align center"));

        List<Playlist> playlists = musicController.getUserPlaylist(MainFrame.loggedUser.getUserId());
                
        for (Playlist playlist : playlists) {
            JPanel container = new JPanel(new MigLayout("wrap"));

            JLabel coverImage = new JLabel();
            coverImage.setOpaque(false);
            ImageHelper.loadAsyncImage(playlist.getCoverUrl(), coverImage, 100, 100, true);

            // Judul Playlist (baris kedua)
            JLabel playlistTitleLabel = new JLabel(StringHelper.truncateString(playlist.getTitle(), 10));
            playlistTitleLabel.setOpaque(false);
            playlistTitleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
            playlistTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
            
            container.setOpaque(true);
            container.add(coverImage);
            container.add(playlistTitleLabel);
            container.setPreferredSize(new Dimension(110, 120));
            
            container.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedAlbum != null) {
                            // Reset the background color of the previous selected panel
                            selectedAlbum.setBackground(null);
                        }
                    selectedAlbum = container;
                    container.setBackground(new Color(85, 83, 85));
                    addedPlaylist = playlist.getPlaylistId();
                }
            });
            addToListPanel.add(container);
        }
                
        JScrollPane scrollPane = new JScrollPane(addToListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(true);
        scrollPane.setPreferredSize(new Dimension(350, 400));
        
        JButton BSubmit = new JButton("Add");
        BSubmit.setPreferredSize(new Dimension(80, 35));
        BSubmit.setFont(new Font("Tahoma", Font.PLAIN, 16));
        BSubmit.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#FFFFFF;"
                + "foreground:#000000;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        BSubmit.addActionListener(e -> {
            if (!addedPlaylist.isEmpty()) {
                musicController.addToPlaylist(songid, addedPlaylist);
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                FlatAnimatedLafChange.showSnapshot();
                cardLayout.show(mainPanel, "panelHome");
                renderUlang();
                morePlaylistScreen.renderUlang();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
                addedPlaylist = "";
                playlistDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(
                owner,
                "Silahkan pilih playlist",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            }
        });
        
        layoutPanel.add(scrollPane);
        layoutPanel.add(BSubmit, "align right, gaptop 15, gaptop 10");
        // Tambahkan scrollPane ke dalam JDialog
        playlistDialog.getContentPane().add(layoutPanel, BorderLayout.CENTER);

        // Mengatur ukuran dan lokasi dialog
        playlistDialog.setSize(350, 400);
        playlistDialog.setResizable(false);
        playlistDialog.setLocationRelativeTo(owner);
        
        playlistDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                addedPlaylist = "";
            }
        });

        playlistDialog.setVisible(true);
    }
    
   // render ulang ketika ada perubahan data
    public void renderUlang() {
        revalidate();
        repaint();
        init();
    }
    
    // method setter
    public void setMorePlaylistScreen(MorePlaylistScreen morePlaylistScreen) {
        this.morePlaylistScreen = morePlaylistScreen;
    }
    
    private JButton BSetting, BRefreshPage, BAddPlaylist, BLoadMorePlaylist;
    private JLabel playlistHeaderLabel, songHeaderLabel, detailTitleLabel, detailSingerLabel, detailCoverImage, detailAlbumLabel, playlistTitleLabel;
    private JPanel layoutPanel, navbarPanel, navbarPlaylistPanel, playlistPanel, navbarSongPanel, songPanel, songHeaderPanel, mainPanel;
    private JSeparator separator1, separator2;
    private ImageIcon plusIcon;
    private MusicController musicController;
    private DetailScreen detailScreen;
    private MusicPlayer musicPlayer;
    private MorePlaylistScreen morePlaylistScreen;
    public static List<Song> songs;
    public static List<Playlist> playlists;
    private byte counter;
    static JPanel selectedAlbum = null;
    private String addedPlaylist = "";
}
