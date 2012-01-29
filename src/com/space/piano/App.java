package com.space.piano;


public class App {

    private MainWindow mWin;

    public App(String[] args) {
        mWin = new MainWindow(this);
    }

    public void run() {
        mWin.setVisible(true);
    }

}
