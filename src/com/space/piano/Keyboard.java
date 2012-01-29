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
        new Key(69, true, 'Ö'),
            new Key(70, false, 'Å'),
        new Key(71, true, 'Ä'),
        new Key(72, true, '\''),
    };

    private App mApp;
    private MainWindow mWin;
    private int mCols;

    public Keyboard(App app, MainWindow mainWindow) {
        mApp = app;
        mWin = mainWindow;

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

    private void calcKeyPos() {
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
                mApp.play(key.getNote(), true);
                key.setPressed(true);
                repaint();
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                mApp.play(key.getNote(), false);
                key.setPressed(false);
                repaint();
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
                    mApp.play(key.getNote(), true);
                    key.setPressed(true);
                    repaint();
                } else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
                    mApp.play(key.getNote(), false);
                    key.setPressed(false);
                    repaint();
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

}
