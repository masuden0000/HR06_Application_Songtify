/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti;

import com.formdev.flatlaf.FlatDarkLaf;
import com.pbo.projectspoti.View.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author User
 */
public class ProjectSpoti {
    public static void main(String[] args) {
        UIManager.put( "Button.arc", 999 );
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
