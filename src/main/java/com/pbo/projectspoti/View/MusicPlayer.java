/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

/**
 *
 * @author User
 */

import com.formdev.flatlaf.FlatClientProperties;
import com.pbo.projectspoti.Helper.ImageHelper;
import com.pbo.projectspoti.Model.Playlist;
import com.pbo.projectspoti.Model.Song;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import javazoom.jl.decoder.*;
import net.miginfocom.swing.MigLayout;

public class MusicPlayer extends JPanel implements ActionListener {
    public MusicPlayer(JLabel detailTitleLabel, JLabel detailSingerLabel, JLabel detailCoverImage, JLabel detailAlbumLabel, JLabel nextSongLabel, JLabel prevSongLabel) {
        this.detailTitleLabel = detailTitleLabel;
        this.detailSingerLabel = detailSingerLabel;
        this.detailCoverImage = detailCoverImage;
        this.detailAlbumLabel = detailAlbumLabel;
        this.nextSongLabel = nextSongLabel;
        this.prevSongLabel = prevSongLabel;
        
        initUI();
        addActionEvents(); 
    }

    public void initUI() {
        // prepare all panel
        playerPanel = new JPanel();
        buttonPanel = new JPanel();
        controlPanel = new JPanel();
        progressPanel = new JPanel();
        blankPanel = new JPanel();
        infoPanel = new JPanel();
        
        controlPanel.setLayout(new GridLayout(1, 3));
        controlPanel.setBackground(new Color(40,40,40));
        
        // icon and resize
        iconPlay = new ImageIcon("src\\main\\resources\\icons\\play-button.png");
        Image image = iconPlay.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        iconPlay = new ImageIcon(image);
        iconPause = new ImageIcon("src\\main\\resources\\icons\\pause-button.png");
        image = iconPause.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        iconPause = new ImageIcon(image);
        iconSkipNext = new ImageIcon("src\\main\\resources\\icons\\skip-next-button.png");
        image = iconSkipNext.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        iconSkipNext = new ImageIcon(image);
        iconSkipPrevious = new ImageIcon("src\\main\\resources\\icons\\skip-previous-button.png");
        image = iconSkipPrevious.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        iconSkipPrevious = new ImageIcon(image);
        iconNext = new ImageIcon("src\\main\\resources\\icons\\next-button.png");
        image = iconNext.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        iconNext = new ImageIcon(image);
        iconPrevious = new ImageIcon("src\\main\\resources\\icons\\previous-button.png");
        image = iconPrevious.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        iconPrevious = new ImageIcon(image);
        playDetailIcon = new ImageIcon("src\\main\\resources\\icons\\play-green.png");
        image = playDetailIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        playDetailIcon = new ImageIcon(image);
        pauseDetailIcon = new ImageIcon("src\\main\\resources\\icons\\pause-green.png");
        image = pauseDetailIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        pauseDetailIcon = new ImageIcon(image);
        
        // panel progress dan button di tengah
        // button
        play = new JButton(iconPlay);
        pause = new JButton(iconPause);
        skipNext = new JButton(iconSkipNext);
        skipPrevious = new JButton(iconSkipPrevious);
        next = new JButton(iconNext);
        previous = new JButton(iconPrevious);
        buttonPanel.setLayout(new GridLayout(1, 5, 10, 10));
        buttonPanel.setBackground(new Color(40,40,40));
        buttonPanel.setPreferredSize(new Dimension(200, 15));
        // progres bar
        UIManager.put("ProgressBar.arc", 40);
        UIManager.put("ProgressBar.background", new Color(119, 119, 119));
        UIManager.put("ProgressBar.foreground", new Color(14, 242, 68));
        progress = new JProgressBar(0, 0, 100);
        progress.setPreferredSize(new Dimension(1000, 7));
        progressPanel.add(progress);
        // button panel yg berisi 5 button ditengah
        buttonPanel.add(previous);
        buttonPanel.add(skipPrevious);
        buttonPanel.add(play);
        buttonPanel.add(skipNext);
        buttonPanel.add(next);
        // panel progress dan button
        playerPanel.setBackground(new Color(40,40,40));
        playerPanel.setLayout(new MigLayout("center", "", "[]15[]"));
        playerPanel.add(buttonPanel, "center, wrap");
        playerPanel.add(progress);
        playerPanel.setPreferredSize(new Dimension(400, 60));
        
        // panel info kiri
        songTitle = new JLabel("");
        singer = new JLabel("");
        songTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        singer.setFont(new Font("Tahoma", Font.PLAIN, 16));
        infoPanel.setLayout(new MigLayout("aligny top", ""));
        infoPanel.setBackground(new Color(40,40,40));
        
        infoPanel.add(songTitle, "wrap");
        infoPanel.add(singer);
        
        // blank panel di kanan
        blankPanel.setBackground(new Color(40,40,40));
        
        // panel untuk menampung semua
        controlPanel.add(infoPanel);
        controlPanel.add(playerPanel);
        controlPanel.add(blankPanel);

        play.setBackground(Color.WHITE);
        play.setPreferredSize(new Dimension(15,15));
        pause.setBackground(Color.WHITE);
        pause.setPreferredSize(new Dimension(15,15));
        skipNext.setBackground(Color.WHITE);
        skipNext.setPreferredSize(new Dimension(15,15));
        skipPrevious.setBackground(Color.WHITE);
        skipPrevious.setPreferredSize(new Dimension(15,15));
        next.setBackground(Color.WHITE);
        next.setPreferredSize(new Dimension(15,15));
        previous.setBackground(Color.WHITE);
        previous.setPreferredSize(new Dimension(15,15));

        add(controlPanel);
        putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:50;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                controlPanel.setPreferredSize(new Dimension(MainFrame.SCREEN_WIDTH - 50, 120));
                progress.setPreferredSize(new Dimension(500, 7));
            }
        });
    }
    
     // Play new song
    public void PlayNew(List<Song> songs, String songId) {                
        if(!isStart) {
            play.doClick();
            player.close();
            isStart = true;
        }
                
        files = new ArrayList();
        listSongs = new ArrayList<>();
        
        byte counter = 0;
        nextCounter = 0;
        
        for (Song song : songs) {
            if(songId.equals(song.getSongId())) {
                files.add(counter, new File(song.getPath()));
                listSongs.add(counter, song);
                counter++;
            } else if (counter > 0) {
                files.add(counter, new File(song.getPath()));
                listSongs.add(counter, song);
                counter++;
            } else {
                files.add(new File(song.getPath()));
                listSongs.add(song);
            }
        }
        
        myFile = files.get(order);
        filename = files.get(order).getName();
        filePath = files.get(order).getPath();
        
        play.doClick();
        
        if(!isPlay && isStart) {
            play.doClick();
        }
    }
    // Play new playlist
    public void PlayNew(Playlist playlist) {
        play.doClick();
        if(!isStart) {
            player.close();
            isStart = true;
        }
        
        
        nextCounter = 0;
        files = new ArrayList();
        listSongs = new ArrayList<>();
                
        for (Song song : playlist.getPlayList()) {
                files.add(new File(song.getPath()));
                listSongs.add(song);
        }
        
        myFile = files.get(order);
        filename = files.get(order).getName();
        filePath = files.get(order).getPath();
        
        play.doClick();
    }

    public void addActionEvents() {
        //registering action listener to buttons
        play.addActionListener(this);
        pause.addActionListener(this);
        skipNext.addActionListener(this);
        skipPrevious.addActionListener(this);
        next.addActionListener(this);
        previous.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(play)) {
            if (!isPlay && !isStart) {
                if (filename != null) {
                    play.setIcon(iconPause);
                    isPlay = true;
                    executorService.submit(runnableResume);
                } else {
                    songTitle.setText("Silahkan Pilih Lagu!");
                }
            } else if(!isPlay && isStart) {
                if (filename != null) {
                    play.setIcon(iconPause);
                    isPlay = true;
                    isStart = false;
                    executorService.submit(runnablePlay);
                    
                    if(listSongs.size() == 1) {
                        nextSongLabel.setText(listSongs.get(order).getSinger()+ " - " + listSongs.get(order).getTitle());
                    } else {
                        nextSongLabel.setText(listSongs.get(order + 1).getSinger()+ " - " + listSongs.get(order + 1).getTitle());
                    }
                    prevSongLabel.setText(listSongs.get(listSongs.size()-1).getSinger()+ " - " + listSongs.get(listSongs.size()-1).getTitle());
                    songTitle.setText(listSongs.get(order).getTitle());
                    singer.setText(listSongs.get(order).getSinger());
                } else {
                    songTitle.setText("Silahkan Pilih Lagu!");
                }
            } else {
                 if (player != null && filename != null) {
                    try {
                        isPlay = false;
                        play.setIcon(iconPlay);
                        pauseLength = fileInputStream.available();
                        player.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        if (e.getSource().equals(pause)) {
            if (player != null && filename != null) {
                try {
                    isPlay = false;
                    pauseLength = fileInputStream.available();
                    player.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (e.getSource().equals(skipNext)) {
            if (player != null && filename != null) {
                try {
                    fileInputStream.skip(100000);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
         if (e.getSource().equals(skipPrevious)) {
            if (player != null && filename != null) {
                try {
                    if((totalLength - fileInputStream.available()) - 100000 > 0) {
                        fileInputStream.skip(-100000);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (e.getSource().equals(next)) {
            if (filename != null) {
                dumpFile = files.get(order);
                files.remove(order);
                files.add(dumpFile);
                
                dumpSong = listSongs.get(order);
                listSongs.remove(order);
                listSongs.add(dumpSong);
                
                myFile = files.get(order);
                filename = files.get(order).getName();
                filePath = files.get(order).getPath();
                
                player.close();
                
                isPlay = true;
                play.setIcon(iconPause);
                executorService.submit(runnablePlay);
                detailTitleLabel.setText(listSongs.get(order).getTitle());
                detailSingerLabel.setText(listSongs.get(order).getSinger());
                detailCoverIcon = ImageHelper.createImageFromURL(listSongs.get(order).getCover_url(), 210, 210, true);
                if (detailCoverIcon != null) {
                    // Tampilkan gambar di dalam JLabel
                    detailCoverImage.setIcon(detailCoverIcon);
                } else {
                    System.out.println("Failed to load image from URL: " + listSongs.get(order).getCover_url());
                }
                detailAlbumLabel.setText(listSongs.get(order).getAlbum());
                if(listSongs.size() == 1) {
                    nextSongLabel.setText(listSongs.get(order).getSinger()+ " - " + listSongs.get(order).getTitle());
                } else {
                    nextSongLabel.setText(listSongs.get(order + 1).getSinger()+ " - " + listSongs.get(order + 1).getTitle());
                }
                prevSongLabel.setText(listSongs.get(listSongs.size()-1).getSinger()+ " - " + listSongs.get(listSongs.size()-1).getTitle());
                songTitle.setText(listSongs.get(order).getTitle());
                singer.setText(listSongs.get(order).getSinger());
                MainScreen.isPlayButton.setIcon(playDetailIcon);
            } else {
                songTitle.setText("No File was selected!");
            }
        }
        if (e.getSource().equals(previous)) {
            if (filename != null) {
                dumpFile = files.get(files.size()-1);
                files.remove(files.size()-1);
                files.add(order,dumpFile);
                
                dumpSong = listSongs.get(listSongs.size()-1);
                listSongs.remove(listSongs.size()-1);
                listSongs.add(order,dumpSong);

                myFile = files.get(order);
                filename = files.get(order).getName();
                filePath = files.get(order).getPath();
                
                player.close();
                
                isPlay = true;
                executorService.submit(runnablePlay);
                detailTitleLabel.setText(listSongs.get(order).getTitle());
                detailSingerLabel.setText(listSongs.get(order).getSinger());
                detailCoverIcon = ImageHelper.createImageFromURL(listSongs.get(order).getCover_url(), 210, 210, true);
                if (detailCoverIcon != null) {
                    // Tampilkan gambar di dalam JLabel
                    detailCoverImage.setIcon(detailCoverIcon);
                } else {
                    System.out.println("Failed to load image from URL: " + listSongs.get(order).getCover_url());
                }
                detailAlbumLabel.setText(listSongs.get(order).getAlbum());
                if(listSongs.size() == 1) {
                    nextSongLabel.setText(listSongs.get(order).getSinger()+ " - " + listSongs.get(order).getTitle());
                } else {
                    nextSongLabel.setText(listSongs.get(order + 1).getSinger()+ " - " + listSongs.get(order + 1).getTitle());
                }
                prevSongLabel.setText(listSongs.get(listSongs.size()-1).getSinger()+ " - " + listSongs.get(listSongs.size()-1).getTitle());
                songTitle.setText(listSongs.get(order).getTitle());
                singer.setText(listSongs.get(order).getSinger());
                MainScreen.isPlayButton.setIcon(playDetailIcon);
            } else {
                songTitle.setText("No File was selected!");
            }
        }
    }

    Runnable runnablePlay = new Runnable() {
        @Override
        public void run() {
            try {
                fileInputStream = new FileInputStream(myFile);
                Bitstream bitstream = new Bitstream(new FileInputStream(filePath));

                Header frameHeader = bitstream.readFrame();
                int bitrate = frameHeader.bitrate();

                bitstream.close();
                
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                totalLength = fileInputStream.available();
                executorProgress.submit(runnableProgress);
                
                player.play();//starting music
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    
    Runnable runnableProgress = new Runnable() {
        @Override
        public void run() {
            while(isPlay) {
                try {
                    if (player.isComplete()) {
                        isStart = true;
                        player.close();
                        next.doClick();
                        break;
                    }
                    val = (int) ( ((double) (totalLength - bufferedInputStream.available()) / totalLength) * 100 );
                    progress.setValue(val);
                    Thread.sleep(1000);
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    };

    Runnable runnableResume = new Runnable() {
        @Override
        public void run() {
            try {
                fileInputStream = new FileInputStream(myFile);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                fileInputStream.skip(totalLength - pauseLength);
                
                executorProgress.submit(runnableProgress);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    // gui init
    private JLabel songTitle, singer, detailTitleLabel, detailSingerLabel, detailCoverImage, detailAlbumLabel, nextSongLabel, prevSongLabel;
    private JPanel playerPanel, controlPanel, buttonPanel, progressPanel, blankPanel, infoPanel;
    private ImageIcon iconPlay, iconPause, iconSkipNext, iconSkipPrevious, iconNext, iconPrevious;
    private JButton play, pause, skipNext, skipPrevious , next, previous;
    private JProgressBar progress;
    // song init
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private File myFile = null;
    private String filename, filePath;
    private long totalLength, pauseLength;
    private Player player;
    private boolean isPlay = false;
    private boolean isStart = true;
    String[] title;
    private List<Song> listSongs;
    private Song dumpSong;
    private ImageIcon detailCoverIcon, playDetailIcon, pauseDetailIcon;
    // Files init
    private List<File> files;
    private File dumpFile;
    final private int order = 0;
    private int val;
    private byte nextCounter;
    // Thread init
    final private int threadPriority = Thread.MAX_PRIORITY;
    final private CustomThreadFactory threadFactory = new CustomThreadFactory(threadPriority);
    final private ExecutorService executorService = Executors.newFixedThreadPool(1, threadFactory);
    final private ExecutorService executorProgress = Executors.newFixedThreadPool(1, threadFactory);
}

class CustomThreadFactory implements ThreadFactory {
    private int priority;

    public CustomThreadFactory(int priority) {
        this.priority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(priority);
        return thread;
    }
}
