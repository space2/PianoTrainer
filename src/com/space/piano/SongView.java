package com.space.piano;

import java.util.Vector;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class SongView extends JComponent {

    private App mApp;
    private MainWindow mWin;
    private Song mSong;
    private int mTime;
    private Keyboard mKeyb;
    private int mEventIdx = 0;

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
        repaint();
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

    public void getNewNoteEvents(Vector<NoteEvent> vec) {
        while (mEventIdx < mSong.getNoteEventCount()) {
            NoteEvent ev = mSong.getNoteEvent(mEventIdx);
            if (ev.getTime() <= mTime) {
                mEventIdx++;
                vec.add(ev);
            } else {
                break;
            }
        }
    }

}
