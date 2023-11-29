/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.Model;

import java.util.List;

/**
 *
 * @author User
 */
public class Playlist {
    private String playlistId;
    private String title;
    private String description;
    private String author;
    private String coverUrl;
    private List<Song> playList;
    
    public Playlist(String playlistId, String title, String description, String author, String coverUrl, List<Song> playList) {
        this.playlistId = playlistId;
        this.title = title;
        this.description = description;
        this.author = author;
        this.coverUrl = coverUrl;
        this.playList = playList;
    }
    
    //getter
    public String getPlaylistId() {
        return playlistId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    
    public String getAuthor() {
        return author;
    }

    public String getCoverUrl() {
        return coverUrl;
    }
    
    public List<Song> getPlayList() {
        return playList;
    }
}
