package com.space.piano;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class SongView extends JComponent {

    private App mApp;
    private MainWindow mWin;
    private Song mSong;
    private int mTime;
    private Keyboard mKeyb;

    public SongView(App app, MainWindow win) {
        mApp = app;
        mWin = win;
    }

    public App getApp() {
        return mApp;
    }

    public MainWindow getWindow() {
        return mWin;
    }

    public void setSong(Song song) {
        mSong = song;
        mTime = 0;
    }

    public Song getSong() {
        return mSong;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public int getTime() {
        return mTime;
    }

    public void setKeyboard(Keyboard keyb) {
        mKeyb = keyb;
    }

    public int getFirstNote() {
        return mKeyb.getFirstNote();
    }

    public int getLastNote() {
        return mKeyb.getLastNote();
    }

    public Keyboard getKeyboard() {
        return mKeyb;
    }

}
