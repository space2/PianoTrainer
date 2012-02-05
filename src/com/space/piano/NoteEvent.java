package com.space.piano;

public class NoteEvent {

    private int mMidiNote;
    private boolean mOn;
    private int mTime;

    public NoteEvent(int midiNote, int time, boolean on) {
        mMidiNote = midiNote;
        mTime = time;
        mOn = on;
    }

    public int getMidiNote() {
        return mMidiNote;
    }

    public int getTime() {
        return mTime;
    }
    public boolean isOn() {
        return mOn;
    }

}
