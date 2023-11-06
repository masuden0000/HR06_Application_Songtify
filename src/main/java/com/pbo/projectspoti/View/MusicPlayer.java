/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

/**
 *
 * @author User
 */

import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import javazoom.jl.decoder.*;

public class MusicPlayer extends JPanel implements ActionListener {

    private JLabel songTitle, singer;
    private JPanel playerPanel, controlPanel, buttonPanel, progressPanel, blankPanel, infoPanel;
    private Icon iconPlay, iconPause, iconSkipNext, iconSkipPrevious, iconNext, iconPrevious;
    private JButton play, pause, skipNext, skipPrevious , next, previous;
    private JProgressBar progress;
    
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private File myFile = null;
    private String filename, filePath;
    private long totalLength, pauseLength;
    private Player player;
    private boolean isPlay = false;
    private boolean isStart = true;
    String[] title;
    
    private List<File> files;
    private File dumpFile;
    final private int order = 0;
    private int val;
    
    final private int threadPriority = Thread.MAX_PRIORITY;
    final private CustomThreadFactory threadFactory = new CustomThreadFactory(threadPriority);
    final private ExecutorService executorService = Executors.newFixedThreadPool(1, threadFactory);
    final private ExecutorService executorProgress = Executors.newFixedThreadPool(1, threadFactory);
    
    public MusicPlayer() {
        String directoryPath = "src\\main\\resources\\music";
                        
        File directory = new File(directoryPath);
        
        if (directory.exists() && directory.isDirectory()) {
            files = new ArrayList(Arrays.asList(directory.listFiles()));
            
            myFile = files.get(order);
            filename = files.get(order).getName();
            filePath = files.get(order).getPath();

        } else {
            System.out.println("Direktori tidak ditemukan.");
        }

        initUI();
        addActionEvents();
    }

    public void initUI() {
        songTitle = new JLabel("");
        singer = new JLabel("");

        playerPanel = new JPanel();
        buttonPanel = new JPanel();
        controlPanel = new JPanel();
        progressPanel = new JPanel();
        blankPanel = new JPanel();
        infoPanel = new JPanel();

        iconPlay = new ImageIcon("G:\\1ADhifan\\Downloads\\Compressed\\music-player-java-code\\music-player-java-code\\DataFlair-Mp3MusicPlayer\\music-player-icons\\play-button.png");
        iconPause = new ImageIcon("G:\\1ADhifan\\Downloads\\Compressed\\music-player-java-code\\music-player-java-code\\DataFlair-Mp3MusicPlayer\\music-player-icons\\pause-button.png");
        iconSkipNext = new ImageIcon("G:\\1ADhifan\\Downloads\\Compressed\\music-player-java-code\\music-player-java-code\\DataFlair-Mp3MusicPlayer\\music-player-icons\\skip-next-button.png");
        iconSkipPrevious = new ImageIcon("G:\\1ADhifan\\Downloads\\Compressed\\music-player-java-code\\music-player-java-code\\DataFlair-Mp3MusicPlayer\\music-player-icons\\skip-previous-button.png");
        iconNext = new ImageIcon("G:\\1ADhifan\\Downloads\\Compressed\\music-player-java-code\\music-player-java-code\\DataFlair-Mp3MusicPlayer\\music-player-icons\\next-button.png");
        iconPrevious = new ImageIcon("G:\\1ADhifan\\Downloads\\Compressed\\music-player-java-code\\music-player-java-code\\DataFlair-Mp3MusicPlayer\\music-player-icons\\previous-button.png");
        
        UIManager.put("ProgressBar.arc", 40);
        UIManager.put("ProgressBar.background", new Color(119, 119, 119));
        UIManager.put("ProgressBar.foreground", new Color(245,245,245));
        progress = new JProgressBar(0, 0, 100);
        progress.setPreferredSize(new Dimension(350, 7));
        
        play = new JButton(iconPlay);
        pause = new JButton(iconPause);
        skipNext = new JButton(iconSkipNext);
        skipPrevious = new JButton(iconSkipPrevious);
        next = new JButton(iconNext);
        previous = new JButton(iconPrevious);
        
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBackground(new Color(0,0,0));
        progressPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        progressPanel.setBackground(new Color(0,0,0));
        blankPanel.setBackground(new Color(0,0,0));
        
        buttonPanel.add(previous);
        buttonPanel.add(skipPrevious);
        buttonPanel.add(play);
        buttonPanel.add(skipNext);
        buttonPanel.add(next);
        
        progressPanel.add(progress);

        playerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        playerPanel.add(buttonPanel, gbc);
        gbc.insets = new Insets(5, 0,0,0);
        gbc.gridy = 1;
        playerPanel.add(progressPanel, gbc);
        playerPanel.setBackground(new Color(0,0,0));

        controlPanel.setLayout(new GridLayout(1, 3));
        controlPanel.setBackground(new Color(0,0,0));
        
        songTitle.setFont(new Font("Arial", Font.BOLD, 25));
        singer.setFont(new Font("Arial", Font.PLAIN, 15));
        infoPanel.setLayout(new GridLayout(2, 1));
        infoPanel.setBackground(new Color(0,0,0));
        
        infoPanel.add(songTitle);
        infoPanel.add(singer);

        controlPanel.add(infoPanel);
        controlPanel.add(playerPanel);
        controlPanel.add(blankPanel);

        play.setBackground(Color.WHITE);
        play.setPreferredSize(new Dimension(50,50));
        pause.setBackground(Color.WHITE);
        pause.setPreferredSize(new Dimension(50,50));
        skipNext.setBackground(Color.WHITE);
        skipNext.setPreferredSize(new Dimension(35,35));
        skipPrevious.setBackground(Color.WHITE);
        skipPrevious.setPreferredSize(new Dimension(35,35));
        next.setBackground(Color.WHITE);
        next.setPreferredSize(new Dimension(35,35));
        previous.setBackground(Color.WHITE);
        previous.setPreferredSize(new Dimension(35,35));

        setLayout(new GridBagLayout());
        gbc.weightx = 1;
        add(controlPanel, gbc);
        setBackground(new Color(0,0,0));
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
                    songTitle.setText("No File was selected!");
                }
            } 
            else if(!isPlay && isStart) {
                if (filename != null) {
                    play.setIcon(iconPause);
                    isPlay = true;
                    isStart = false;
                    executorService.submit(runnablePlay);
                    
                    title = filename.split(" - ");
                    songTitle.setText(title[1].split(".mp3")[0]);
                    singer.setText(title[0]);
                } else {
                    songTitle.setText("No File was selected!");
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

                myFile = files.get(order);
                filename = files.get(order).getName();
                filePath = files.get(order).getPath();
                
                player.close();
                
                isPlay = true;
                executorService.submit(runnablePlay);
                title = filename.split(" - ");
                songTitle.setText(title[1].split(".mp3")[0]);
                singer.setText(title[0]);
            } else {
                songTitle.setText("No File was selected!");
            }
        }
        if (e.getSource().equals(previous)) {
            if (filename != null) {
                dumpFile = files.get(files.size()-1);
                files.remove(files.size()-1);
                files.add(order,dumpFile);

                myFile = files.get(order);
                filename = files.get(order).getName();
                filePath = files.get(order).getPath();
                
                player.close();
                
                isPlay = true;
                executorService.submit(runnablePlay);
                title = filename.split(" - ");
                songTitle.setText(title[1].split(".mp3")[0]);
                singer.setText(title[0]);
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

    public static void main(String[] args) {
        MusicPlayer mp = new MusicPlayer();
    }
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
