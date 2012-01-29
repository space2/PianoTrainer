package com.space.piano;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

    private App mApp;
    private Keyboard mKeyb;

    public MainWindow(App app) {
        super("Piano");
        mApp = app;

        JPanel root = new JPanel(new BorderLayout());
        setContentPane(root);
        mKeyb = new Keyboard(mApp, this);
        root.add(mKeyb, BorderLayout.SOUTH);
        mKeyb.setPreferredSize(new Dimension(0, 240));

        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
