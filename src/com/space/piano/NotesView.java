package com.space.piano;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

@SuppressWarnings("serial")
public class NotesView extends SongView {

    private static final Color COL_BLUE1 = new Color(0x0000ff);
    private static final Color COL_BLUE2 = new Color(0x4040ff);

    public NotesView(App app, MainWindow win) {
        super(app, win);
    }

    @Override
    public void paint(Graphics g_) {
        Graphics2D g = (Graphics2D) g_;
        int w = getWidth();
        int h = getHeight();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, w, h);

        // Draw the guides
        int fn = getFirstNote();
        int ln = getLastNote();
        Keyboard keyb = getKeyboard();
        keyb.calcKeyPos();
        g.setColor(Color.LIGHT_GRAY);
        for (int n = fn; n <= ln; n++) {
            Key key = keyb.findKeyByNote(n);
            if (key.isWhite()) {
                int nx1 = key.getX1();
                g.drawLine(nx1, 0, nx1, h);
            }
        }

        // Draw the notes
        float scale = 0.1f;
        for (int i = 0; i < getSong().getCount(); i++) {
            Note note = getSong().getNote(i);
            if (note.getNote() >= fn && note.getNote() <= ln) {
                int y1 = (int) ((note.getOnTime() - getTime()) * scale);
                int y2 = (int) ((note.getOffTime() - getTime()) * scale);
                Key key = keyb.findKeyByNote(note.getNote());
                int nx1 = key.getX1();
                int nx2 = key.getX2();
                g.setPaint(new GradientPaint(
                        nx1, h - y2, COL_BLUE1,
                        nx1, h - y1, COL_BLUE2));
                g.fillRect(nx1, h - y2, nx2 - nx1, y2 - y1);
            }
        }
        g.setPaint(null);
    }

}
