package com.space.piano;

import java.awt.Color;
import java.awt.Graphics;

public class Key {

    private int mNote;
    private boolean mWhite;
    private char mLabel;
    private int mX1;
    private int mY1;
    private int mX2;
    private int mY2;

    public Key(int midiNote, boolean white, char label) {
        mNote = midiNote;
        mWhite = white;
        mLabel = label;
    }

    public boolean isWhite() {
        return mWhite;
    }

    public char getLabel() {
        return mLabel;
    }

    public int getNote() {
        return mNote;
    }

    public void setBounds(int x1, int y1, int x2, int y2) {
        mX1 = x1;
        mY1 = y1;
        mX2 = x2;
        mY2 = y2;

    }

    public void paint(Graphics g) {
        if (mWhite) {
            g.setColor(Color.WHITE);
            g.fillRect(mX1, mY1, mX2 - mX1, mY2 - mY1);
            g.setColor(Color.BLACK);
            g.drawRect(mX1, mY1, mX2 - mX1 - 1, mY2 - mY1 - 1);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(mX1, mY1, mX2 - mX1, mY2 - mY1);
            g.setColor(Color.WHITE);
        }

    }

}
