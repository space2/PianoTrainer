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
            songView.getNewNoteEvents(mWaitingFor);
            if (mWaitingFor.size() > 0) {
                pauseTime();
            }
        } else {
            if (mWaitingFor.size() == 0) {
                resumeTime();
            }
        }
    }

}
