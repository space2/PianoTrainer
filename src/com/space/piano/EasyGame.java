package com.space.piano;

import java.util.Vector;

public class EasyGame extends Game {

    private int mTime;
    private int mRefTime;
    private long mLastPausedAt;
    private float mSpeed = 0.5f;
    private boolean mTimeRunning = false;
    private Vector<NoteEvent> mWaitingFor = new Vector<NoteEvent>();

    public EasyGame(App app) {
        super(app);
    }

    @Override
    public void start() {
        super.start();
        mRefTime = -5*1000;
        mLastPausedAt = System.currentTimeMillis();
        mTimeRunning = true;
    }

    @Override
    public void stop() {
        super.stop();
    }

    private void resumeTime() {
        mTimeRunning = true;
        mLastPausedAt = System.currentTimeMillis();
    }

    private void pauseTime() {
        mTimeRunning = false;
        mRefTime += (int) ((System.currentTimeMillis() - mLastPausedAt) * mSpeed);
    }

    @Override
    protected void tick() {
        super.tick();
        if (mTimeRunning) {
            mTime = (int) ((System.currentTimeMillis() - mLastPausedAt) * mSpeed) + mRefTime;
            SongView songView = getApp().getSongView();
            songView.setTime(mTime);
            synchronized (mWaitingFor) {
                songView.getNewNoteEvents(mWaitingFor);
                filterEvents(mWaitingFor);
                if (mWaitingFor.size() > 0) {
                    pauseTime();
                }
            }
        } else {
            synchronized (mWaitingFor) {
                if (mWaitingFor.size() == 0) {
                    resumeTime();
                }
            }
        }
    }

    private void filterEvents(Vector<NoteEvent> vec) {
        int cnt = vec.size();
        for (int i = cnt - 1; i >= 0; i--) {
            NoteEvent ev = vec.get(i);
            if (!getApp().isKeyVisible(ev.getMidiNote())) {
                getApp().play(ev.getMidiNote(), ev.isOn());
                vec.remove(i);
            } else if (ev.isOn() == false) {
                vec.remove(i);
            }
        }
    }

    public void onKey(int note, boolean on) {
        synchronized (mWaitingFor) {
            int cnt = mWaitingFor.size();
            for (int i = cnt - 1; i >= 0; i--) {
                NoteEvent ev = mWaitingFor.get(i);
                if (ev.getMidiNote() == note && ev.isOn() == on) {
                    mWaitingFor.remove(i);
                }
            }
        }
    }

}
