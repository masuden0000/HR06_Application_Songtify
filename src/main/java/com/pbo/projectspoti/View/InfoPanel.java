/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.View;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author User
 */
public class InfoPanel extends JPanel{
    public InfoPanel() {
        setLayout(new MigLayout("wrap 1, align y center","", "[][][]push[]push[][][]"));
        setBorder(new EmptyBorder(5, 10, 5, 10));
        putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");
        setPreferredSize(new Dimension(250, 570));
    }
}
