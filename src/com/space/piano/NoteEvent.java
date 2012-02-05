package com.space.piano;

public class NoteEvent {

    private Note mNote;
    private boolean mOn;
    private int mTime;

    public NoteEvent(Note note, int time, boolean on) {
        mNote = note;
        mTime = time;
        mOn = on;
    }

    public Note getNote() {
        return mNote;
    }

    public int getTime() {
        return mTime;
    }
    public boolean isOn() {
        return mOn;
    }

}
