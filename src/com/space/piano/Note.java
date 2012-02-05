package com.space.piano;

public class Note {

    private int mNote;
    private int mOn; // in ms
    private int mOff; // in ms

    public Note(int note, int on, int off) {
        mNote = note;
        mOn = on;
        mOff = off;
    }

    public int getMidiNote() {
        return mNote;
    }

    public int getOnTime() {
        return mOn;
    }

    public int getOffTime() {
        return mOff;
    }

    public void setOffTime(int off) {
        mOff = off;
    }

}
