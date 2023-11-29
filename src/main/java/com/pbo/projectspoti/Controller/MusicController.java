/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.Controller;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.pbo.projectspoti.Model.Database;
import com.pbo.projectspoti.Model.MusicModel;
import com.pbo.projectspoti.Model.Playlist;
import com.pbo.projectspoti.Model.Song;
import com.pbo.projectspoti.View.MusicPlayer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class MusicController {
    final private Database database;
    final private Connection connect;
    final private MusicModel model;
    
    public MusicController() {
        database = Database.getInstance();
        connect = database.getConnection();
        model = new MusicModel(this.connect);
    }
       
    public List<Song> initSong () {
        return model.getAllSongs();
    }
    
    public List<Playlist> initPlaylist () {
        return model.getAllPlaylist();
    }
    
    public void playSong (String songId, MusicPlayer musicPlayer, List<Song> songs) {
        musicPlayer.PlayNew(songs, songId);
    }
    
    public void playPlaylist (MusicPlayer musicPlayer, Playlist playlist) {
        musicPlayer.PlayNew(playlist);
    }
    
    public List<Playlist> getUserPlaylist (String userid) {
        return model.getAllUserPlaylist(userid);
    }
    
    public void addToPlaylist(String songid, String playlistid) {
        model.addToPlaylist(songid, playlistid);
    }
    
    public void deleteFromPlaylist(String songid, String playlistid) {
        model.deleteFromPlaylist(songid, playlistid);
    }
    
    public void deletePlaylist(String playlistid) {
        model.deletePlaylist(playlistid);
    }
    
    public Playlist getPlaylistById(String playlistid) {
        return model.getPlaylistById(playlistid);
    }
    
    public boolean addPlaylist (String title, String deskripsi, String coverUrl, String author) {
        String playlistId = "play-" + NanoIdUtils.randomNanoId();
        playlistId = playlistId.substring(0, Math.min(playlistId.length(), 15));
        title = title.trim();
        
        // Validasi
        if(title.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Title Required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(coverUrl.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Cover Required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(deskripsi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Description Required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            this.model.addPlaylist(playlistId, title, deskripsi, coverUrl, author);
            
            return true;
        } catch (SQLException er) {
            JOptionPane.showMessageDialog(null, "Kesalahan Server.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
