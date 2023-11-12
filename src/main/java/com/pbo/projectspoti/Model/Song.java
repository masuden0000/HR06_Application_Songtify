/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.Model;

/**
 *
 * @author User
 */
public class Song {
    private String songId;
    private String title;
    private String singer;
    private String path;
    private String cover_url;

    public Song(String songId, String title, String singer, String path, String cover_url) {
        this.songId = songId;
        this.title = title;
        this.singer = singer;
        this.path = path;
        this.cover_url = cover_url;
    }

    // getters

    public String getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public String getSinger() {
        return singer;
    }

    public String getPath() {
        return path;
    }

    public String getCover_url() {
        return cover_url;
    }

    @Override
    public String toString() {
        return this.title + " - " + this.singer;
    }
}
