package com.space.piano;


public class Game implements Runnable {

    private App mApp;
    private boolean mRunning;
    private Thread mThread;

    public Game(App app) {
        mApp = app;
    }

    public App getApp() {
        return mApp;
    }

    public boolean isRunning() {
        return mRunning;
    }

    public void start() {
        mRunning = true;
        mThread = new Thread(this);
        mThread.start();
    }

    public void stop() {
        mThread = null;
        mRunning = false;
    }

    @Override
    public void run() {
        while (mThread != null) {
            tick();
            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    protected void tick() {
        // NOP
    }

    public void onKey(int note, boolean on) {
        // NOP
    }

}
