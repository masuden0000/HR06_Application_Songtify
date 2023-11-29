/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.pbo.projectspoti.View.MainFrame;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author User
 */
public class ProjectSpoti {
    public static void main(String[] args) {
        // Styling default untuk komponen
        FlatLaf.registerCustomDefaultsSource("themes");
        // Install font Roboto
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        try
        {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (Exception ex)
        {
            System.err.println("Failed to initialize LaF");
        }
        EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
