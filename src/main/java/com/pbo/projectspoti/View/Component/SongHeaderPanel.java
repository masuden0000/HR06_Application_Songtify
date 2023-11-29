/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View.Component;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author User
 */
public class SongHeaderPanel extends JPanel {
    public SongHeaderPanel() {
        setLayout(new MigLayout());
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        
        // Title Header
        titleHeaderLabel = new JLabel("Title");
        titleHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        
        titleHeaderPanel = new JPanel(new GridBagLayout());
        titleHeaderPanel.setPreferredSize(new Dimension(255, 5));
        titleHeaderPanel.add(titleHeaderLabel, gbc);
        
        // Album Header
        albumHeaderLabel= new JLabel("Album");
        albumHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        
        albumHeaderPanel = new JPanel(new GridBagLayout());
        albumHeaderPanel.setPreferredSize(new Dimension(135, 5));
        albumHeaderPanel.add(albumHeaderLabel, gbc);
        
        // Duration Header
        durationHeaderLabel = new JLabel("Duration");
        durationHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        durationHeaderPanel = new JPanel(new GridBagLayout());
        durationHeaderPanel.setPreferredSize(new Dimension(50, 5));
        durationHeaderPanel.add(durationHeaderLabel, gbc);
        
        // Menambahkan ke panel utama
        add(titleHeaderPanel);
        add(albumHeaderPanel);
        add(durationHeaderPanel);
    }
    
    final private GridBagConstraints gbc;
    final private JLabel titleHeaderLabel, albumHeaderLabel, durationHeaderLabel;
    final private JPanel titleHeaderPanel, albumHeaderPanel, durationHeaderPanel;
}
