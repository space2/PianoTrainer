package com.space.piano;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Keyboard extends JComponent {

    private static final Key KEYS[] = {
        new Key(40, true, (char)0),
        new Key(41, true, (char)0),
            new Key(42, false, (char)0),
        new Key(43, true, (char)0),
            new Key(44, false, (char)0),
        new Key(45, true, (char)0),
            new Key(46, false, (char)0),
        new Key(47, true, (char)0),
        new Key(48, true, (char)0),
            new Key(49, false, (char)0),
        new Key(50, true, (char)0),
            new Key(51, false, (char)0),
        new Key(52, true, (char)0),
        new Key(53, true, 'A'),
            new Key(54, false, 'W'),
        new Key(55, true, 'S'),
            new Key(56, false, 'E'),
        new Key(57, true, 'D'),
            new Key(58, false, 'R'),
        new Key(59, true, 'F'),
        new Key(60, true, 'G'),
            new Key(61, false, 'Y'),
        new Key(62, true, 'H'),
            new Key(63, false, 'U'),
        new Key(64, true, 'J'),
        new Key(65, true, 'K'),
            new Key(66, false, 'O'),
        new Key(67, true, 'L'),
            new Key(68, false, 'P'),
        new Key(69, true, '…'),
            new Key(70, false, ''),
        new Key(71, true, '€'),
        new Key(72, true, '\''),
    };

    private App mApp;
    private int mCols;

    public Keyboard(App app, MainWindow mainWindow) {
        mApp = app;

        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
        setFocusable(true);
        requestFocus();

        // Count white keys
        for (Key k : KEYS) {
            if (k.isWhite()) {
                mCols++;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        calcKeyPos();

        int w = getWidth();
        int h = getHeight();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);
        for (Key k : KEYS) {
            if (k.isWhite()) {
                k.paint(g);
            }
        }
        for (Key k : KEYS) {
            if (!k.isWhite()) {
                k.paint(g);
            }
        }
    }

    public void calcKeyPos() {
        // Calculate position
        int w = getWidth();
        int h = getHeight();
        int idx = 0;
        for (Key k : KEYS) {
            int x1 = idx * w / mCols;
            int x2 = (idx + 1) * w / mCols;
            if (k.isWhite()) {
                k.setBounds(x1, 0, x2, h);
                idx++;
            } else {
                int bw = w / mCols / 4;
                k.setBounds(x1 - bw, 0, x1 + bw, h / 2);
            }
        }
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        Key key = findKey(e.getKeyChar());
        if (key != null) {
            if (e.getID() == KeyEvent.KEY_PRESSED && !key.isPressed()) {
                mApp.onKey(key.getNote(), true);
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                mApp.onKey(key.getNote(), false);
            }
        }
        e.consume();
    }

    private Key findKey(char keyChar) {
        keyChar = Character.toUpperCase(keyChar);
        for (Key key : KEYS) {
            if (key.getLabel() == keyChar) {
                return key;
            }
        }
        return null;
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Key key = findKey(e.getX(), e.getY());
            if (key != null) {
                if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                    mApp.onKey(key.getNote(), true);
                } else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
                    mApp.onKey(key.getNote(), false);
                }
            }
            e.consume();
        }
    }

    private Key findKey(int x, int y) {
        for (Key k : KEYS) {
            if (!k.isWhite() && k.isInside(x, y)) {
                return k;
            }
        }
        for (Key k : KEYS) {
            if (k.isWhite() && k.isInside(x, y)) {
                return k;
            }
        }
        return null;
    }

    public int getFirstNote() {
        return KEYS[0].getNote();
    }

    public int getLastNote() {
        return KEYS[KEYS.length - 1].getNote();
    }

    public Key findKeyByNote(int n) {
        for (Key k : KEYS) {
            if (k.getNote() == n) {
                return k;
            }
        }
        return null;
    }

    public boolean isKeyVisible(int midiNote) {
        return midiNote >= getFirstNote() && midiNote <= getLastNote();
    }

    public void onKey(int note, boolean on) {
        Key key = findKeyByNote(note);
        if (key != null) {
            key.setPressed(on);
            repaint();
        }
    }

}
