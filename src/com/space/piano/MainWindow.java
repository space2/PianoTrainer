package com.space.piano;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

    private App mApp;
    private Keyboard mKeyb;
    private SongView mSongView;
    private JLabel mScoreView;

    public MainWindow(App app) {
        super("Piano");
        mApp = app;

        JPanel root = new JPanel(new BorderLayout());
        setContentPane(root);
        mKeyb = new Keyboard(mApp, this);
        root.add(mKeyb, BorderLayout.SOUTH);
        mKeyb.setPreferredSize(new Dimension(0, 240));

        mSongView = new NotesView(mApp, this);
        mSongView.setKeyboard(mKeyb);
        root.add(mSongView, BorderLayout.CENTER);

        JPanel header = new JPanel();
        root.add(header, BorderLayout.NORTH);
        mScoreView = new JLabel();
        header.add(mScoreView);

        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setSong(Song song) {
        mSongView.setSong(song);
    }

    public SongView getSongView() {
        return mSongView;
    }

    public Keyboard getKeyboard() {
        return mKeyb;
    }

    public void setScore(int score) {
        mScoreView.setText("Score: " + score);
    }
}
