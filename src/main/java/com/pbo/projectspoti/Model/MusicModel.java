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
                String album = resultSet.getString("album");
                String duration = resultSet.getString("duration");
                String path = resultSet.getString("path");
                String cover_url = resultSet.getString("cover_url");
                
                Song song = new Song(songId, title, singer, album, duration, path, cover_url);
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
                String album = resultSet.getString("album");
                String duration = resultSet.getString("duration");
                String path = resultSet.getString("path");
                String cover_url = resultSet.getString("cover_url");
                
                song = new Song(songId, title, singer, album, duration, path, cover_url);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return song;
    }
    
    public List<Playlist> getAllPlaylist() {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT playlistid, title, description, playlist, cover_url, username " +
                "FROM playlist " +
                "INNER JOIN users " +
                "ON playlist.author = users.userid";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                List<Song> songs = new ArrayList<>();
                String playlistId = resultSet.getString("playlistid");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String[] playList = {};
                if (resultSet.getArray("playlist") != null) {
                    playList = (String[]) resultSet.getArray("playlist").getArray();
                }
                String coverUrl = resultSet.getString("cover_url");
                String author = resultSet.getString("username");
                
                for (String songid : playList) {
                    songs.add(getSongById(songid));
                }
                Playlist playlist = new Playlist(playlistId, title, description, author, coverUrl, songs);
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return playlists;
    }
    
    public String addPlaylist(String playlistId, String title, String description, String coverUrl, String author) throws SQLException {
        String playlistid = "";
        String sql = "INSERT INTO playlist(playlistid, title, description, cover_url, author) VALUES (?, ?, ?, ?, ?) RETURNING playlistid";
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, playlistId);
                statement.setString(2, title);
                statement.setString(3, description);
                statement.setString(4, coverUrl);
                statement.setString(5, author);
                
               ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    playlistid = resultSet.getString("playlistid");
                }
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return playlistid;
    }
    
     public List<Playlist> getAllUserPlaylist(String userid) {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT playlistid, title, description, cover_url " +
                "FROM playlist WHERE author = ?" ;
         try (PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, userid);
                
               ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String playlistid = resultSet.getString("playlistid");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    String coverUrl = resultSet.getString("cover_url");
                            
                    Playlist playlist = new Playlist(playlistid, title, description, userid, coverUrl, new ArrayList<Song>());
                    playlists.add(playlist);
                }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return playlists;
    }
     
     public Playlist getPlaylistById(String playlistid) {
        Playlist playlist = null;
        String sql = "SELECT * FROM playlist WHERE playlistid = ?" ;
         try (PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, playlistid);
                
               ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    List<Song> songs = new ArrayList<>();
                    String playlistId = resultSet.getString("playlistid");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    String[] playList = {};
                    if (resultSet.getArray("playlist") != null) {
                        playList = (String[]) resultSet.getArray("playlist").getArray();
                    }
                    String coverUrl = resultSet.getString("cover_url");
                    String author = resultSet.getString("author");

                    for (String songid : playList) {
                        songs.add(getSongById(songid));
                    }
                    playlist = new Playlist(playlistId, title, description, author, coverUrl, songs);
                }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return playlist;
    }
     
    public String addToPlaylist(String songid, String playlistid) {
        String playlistId = "";
        String sql = "UPDATE playlist SET playlist = array_append(playlist, ?) WHERE playlistid = ? RETURNING playlistid" ;
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, songid);
                statement.setString(2, playlistid);

               ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    playlistId = resultSet.getString("playlistid");
                }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return playlistid;
    }
    
    public String deletePlaylist(String playlistId) {
        String playlistid = "";
        String sql = "DELETE FROM playlist WHERE playlistid = ? RETURNING playlistid";
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, playlistId);
                
               ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    playlistid = resultSet.getString("playlistid");
                }
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return playlistid;
    }
    
        public String deleteFromPlaylist(String songid, String playlistid) {
        String playlistId = "";
        String sql = "UPDATE playlist SET playlist = ARRAY_REMOVE(playlist, ?) WHERE playlistid = ? RETURNING playlistid" ;
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, songid);
                statement.setString(2, playlistid);

               ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    playlistId = resultSet.getString("playlistid");
                }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return playlistid;
    }
}