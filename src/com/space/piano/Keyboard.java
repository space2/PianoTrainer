package com.space.piano;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Keyboard extends JComponent {

    private static final Key KEYS[] = {
        new Key(52, true, 'A'),
        new Key(53, true, 'S'),
            new Key(54, false, 'E'),
        new Key(55, true, 'D'),
            new Key(56, false, 'R'),
        new Key(57, true, 'F'),
            new Key(58, false, 'T'),
        new Key(59, true, 'G'),
        new Key(60, true, 'H'),
            new Key(61, false, 'U'),
        new Key(62, true, 'J'),
            new Key(63, false, 'I'),
        new Key(64, true, 'K'),
        new Key(65, true, 'L'),
            new Key(66, false, 'P'),
        new Key(67, true, 'Ö'),
            new Key(68, false, 'Å'),
        new Key(69, true, 'Ä'),
            new Key(70, false, '^'),
        new Key(71, true, '\''),
    };

    private App mApp;
    private MainWindow mWin;
    private int mCols;

    public Keyboard(App app, MainWindow mainWindow) {
        mApp = app;
        mWin = mainWindow;

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

}
