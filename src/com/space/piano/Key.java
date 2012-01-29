package com.space.piano;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Key {

    private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 32);

    private int mNote;
    private boolean mWhite;
    private char mLabel;
    private int mX1;
    private int mY1;
    private int mX2;
    private int mY2;

    private boolean mPressed;

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
            g.setColor(mPressed ? Color.LIGHT_GRAY : Color.WHITE);
            g.fillRect(mX1, mY1, mX2 - mX1, mY2 - mY1);
            g.setColor(Color.BLACK);
            g.drawRect(mX1, mY1, mX2 - mX1 - 1, mY2 - mY1 - 1);
        } else {
            g.setColor(mPressed ? Color.DARK_GRAY : Color.BLACK);
            g.fillRect(mX1, mY1, mX2 - mX1, mY2 - mY1);
            g.setColor(Color.WHITE);
        }

        g.setFont(FONT);
        String s = Character.toString(mLabel);
        FontMetrics fm = g.getFontMetrics();
        int w = fm.stringWidth(s);
        g.drawString(s, mX1 + (mX2 - mX1 - w) / 2, mY2 - 5 - fm.getDescent());
    }

    public boolean isInside(int x, int y) {
        return (x >= mX1 && x < mX2 && y >= mY1 && y < mY2);
    }

    public void setPressed(boolean b) {
        mPressed = b;
    }

    public boolean isPressed() {
        return mPressed;
    }

}
