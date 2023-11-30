package com.pbo.projectspoti.Controller;

import com.pbo.projectspoti.View.*;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class FormsManager {
    private MainFrame mainFrame;
    private static FormsManager instance;

    public static FormsManager getInstance() {
        if (instance == null) {
            instance = new FormsManager();
        }
        return instance;
    }

    private FormsManager() {
    }

    public void initApplication(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void showForm(JComponent form) {
        EventQueue.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot();
            mainFrame.setContentPane(form);
            mainFrame.revalidate();
            mainFrame.repaint();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }
}
