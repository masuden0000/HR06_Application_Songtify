/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.Helper;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author User
 */
public class ImageHelper {
    public static void loadAsyncImage(String imageUrl, JLabel imageLabel, int width, int height, boolean isRounded) {
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                // Menampilkan loading
                imageLabel.setText("Loading...");
                imageLabel.setPreferredSize(new Dimension(width, height));

                // Membuat ImageIcon dari URL secara asinkron
                return createImageFromURL(imageUrl, width, height, isRounded);
            }

            @Override
            protected void done() {
                try {
                    // Mengambil ImageIcon dari hasil doInBackground
                    ImageIcon imageIcon = get();

                    // Menyembunyikan loading dan menampilkan ImageIcon jika berhasil diunduh
                    imageLabel.setText(null);
                    if (imageIcon != null) {
                        imageLabel.setIcon(imageIcon);
                    } else {
                        JOptionPane.showMessageDialog(null, "Gagal mengunduh gambar.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // Menjalankan SwingWorker
        worker.execute();
    }
    
    public static ImageIcon createImageFromURL(String imageUrl, int width, int height, boolean isRounded) {
        try {
            // Baca gambar dari URL
            if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
                imageUrl = "http://" + imageUrl;
            }
            URL url = new URL(imageUrl);
            Image originalImage = Toolkit.getDefaultToolkit().getImage(url);

            // Resize gambar
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            if (isRounded) {
                ImageIcon originalIcon = new ImageIcon(resizedImage);
                Image originalImg = originalIcon.getImage();
                BufferedImage roundedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                // Menggambar gambar asli ke gambar dengan sudut yang melengkung (rounded)
                Graphics2D g2d = roundedImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Menggambar gambar dengan sudut yang melengkung
                int cornerRadius = 10; // Atur ukuran sudut bulatan
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius);
                g2d.setClip(roundedRectangle);
                g2d.drawImage(originalImg, 0, 0, width, height, null);
                g2d.dispose();

                return new ImageIcon(roundedImage);
            }

            // Buat ImageIcon dari gambar yang sudah diresize
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
