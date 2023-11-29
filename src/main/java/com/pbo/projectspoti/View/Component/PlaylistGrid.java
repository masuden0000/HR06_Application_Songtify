/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View.Component;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.pbo.projectspoti.Helper.ImageHelper;
import com.pbo.projectspoti.Helper.StringHelper;
import com.pbo.projectspoti.Model.Playlist;
import com.pbo.projectspoti.View.DetailScreen;
import com.pbo.projectspoti.View.MainScreen;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author User
 */
public class PlaylistGrid extends JPanel {
    public PlaylistGrid(Playlist playlist, JPanel mainPanel, DetailScreen detailScreen) {
        setLayout(new MigLayout("wrap, width 120px, height 160px", "[align left]", "[]10[]10[]"));
        putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;");
        // Gambar Playlist
        coverImage = new JLabel();
        
        // Mengatur gambar yang sudah diubah menjadi ikon pada backgroundLabel
        ImageHelper.loadAsyncImage(playlist.getCoverUrl(), coverImage, 120, 120, true);

        // Judul Playlist (baris kedua)
        playlistTitleLabel = new JLabel(StringHelper.truncateString(playlist.getTitle(), 15));
        playlistTitleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        playlistTitleLabel.setFont(new Font("Roboto", Font.BOLD, 14));

        // Pembuat Playlist (baris ketiga)
        playlistAuthorLabel = new JLabel(playlist.getAuthor());
        playlistAuthorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        //Menambahkan ke panel
        add(coverImage, "align center, wrap");
        add(playlistTitleLabel, "wrap");
        add(playlistAuthorLabel);
        setBackground(new Color(67, 71, 73));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Panel dihover, ganti warna
                setBackground(new Color(75, 76, 79));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                // Hover keluar dari panel, kembalikan warna awal
                setBackground(new Color(67, 71, 73));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                FlatAnimatedLafChange.showSnapshot();
                MainScreen.playlistData = playlist;
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "panelDetail");
                detailScreen.renderUlang();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
            }
        });
    }
    
    private JLabel coverImage;
    private JLabel playlistTitleLabel, playlistAuthorLabel;
    private ImageIcon roundedIcon;
}
