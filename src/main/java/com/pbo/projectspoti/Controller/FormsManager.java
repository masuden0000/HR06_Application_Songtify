package com.pbo.projectspoti.Controller;

import com.pbo.projectspoti.View.*;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class FormsManager {
    private MainFrame mainFrame;
    private static FormsManager instance;
    private Stack<JComponent> formStack;

    public static FormsManager getInstance() {
        if (instance == null) {
            instance = new FormsManager();
        }
        return instance;
    }

    private FormsManager() {
        formStack = new Stack<>();
    }

    public void initApplication(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void showForm(JComponent form) {
        EventQueue.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot();
            formStack.push((JComponent) mainFrame.getContentPane()); // Push current form to stack
            mainFrame.setContentPane(form);
            mainFrame.revalidate();
            mainFrame.repaint();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }
    
    public void showPanel(JComponent form) {
        EventQueue.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot();
            formStack.push((JComponent) mainFrame.getContentPane()); // Push current form to stack
            mainFrame.setContentPane(form);
            mainFrame.revalidate();
            mainFrame.repaint();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }

    public void goBack() {
        if (!formStack.isEmpty()) {
            EventQueue.invokeLater(() -> {
                FlatAnimatedLafChange.showSnapshot();
                JComponent previousForm = formStack.pop();
                mainFrame.setContentPane(previousForm);
                mainFrame.revalidate();
                mainFrame.repaint();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
            });
        }
    }
}
