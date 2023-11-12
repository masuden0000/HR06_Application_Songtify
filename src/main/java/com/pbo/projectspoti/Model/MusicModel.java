/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.Model;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class MusicModel {
    private Connection connection;

    public MusicModel(Connection connection) {
        this.connection = connection;
    }
    
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM songs";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String songId = resultSet.getString("songid");
                String title = resultSet.getString("title");
                String singer = resultSet.getString("singer");
                String path = resultSet.getString("path");
                String cover_url = resultSet.getString("cover_url");
                
                Song song = new Song(songId, title, singer, path, cover_url);
                songs.add(song);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return songs;
    }
    
    public Song getSongById(String songId) {
        Song song = null;
        String sql = "SELECT * FROM songs WHERE songid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
             statement.setString(1, songId);
             
             ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String singer = resultSet.getString("singer");
                String path = resultSet.getString("path");
                String cover_url = resultSet.getString("cover_url");
                
                song = new Song(songId, title, singer, path, cover_url);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return song;
    }
    
    public List<Playlist> getAllPlaylist() {
        List<Playlist> playlists = new ArrayList<>();
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT playlistid, title, playlist, username " +
                "FROM playlist " +
                "INNER JOIN users " +
                "ON playlist.author = users.userid";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String playlistId = resultSet.getString("playlistid");
                String title = resultSet.getString("title");
                String[] playList = (String[]) resultSet.getArray("playlist").getArray();
                String author = resultSet.getString("username");
                
                for (String songid : playList) {
                    songs.add(getSongById(songid));
                }
                
                Playlist playlist = new Playlist(playlistId, title, author, songs);
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return playlists;
    }
}
