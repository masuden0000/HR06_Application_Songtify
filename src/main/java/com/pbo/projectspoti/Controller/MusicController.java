/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.Controller;

import com.pbo.projectspoti.Model.Database;
import com.pbo.projectspoti.Model.MusicModel;
import com.pbo.projectspoti.Model.Playlist;
import com.pbo.projectspoti.Model.Song;
import com.pbo.projectspoti.View.MusicPlayer;
import java.sql.Connection;
import java.util.List;

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
}
