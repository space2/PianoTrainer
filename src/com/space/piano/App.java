package com.space.piano;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class App {

    private MainWindow mWin;
    private Synthesizer mSynth;
    private ShortMessage mMsg;
    private Receiver mSynthRcvr;
    private Game mGame;

    public App(String[] args) {
        mWin = new MainWindow(this);

        try {
            mSynth = MidiSystem.getSynthesizer();
            mSynth.open();
            mMsg = new ShortMessage();
            mSynthRcvr = mSynth.getReceiver();

            Sequence seq = MidiSystem.getSequence(getClass().getResourceAsStream("/JingleBells.mid"));
            Song song = new Song(seq);
            mWin.setSong(song);

            mGame = new EasyGame(this);
            mGame.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onKey(int note, boolean on) {
        play(note, on);
        mGame.onKey(note, on);
    }

    public void play(int note, boolean on) {
        try {
            mMsg.setMessage(on ? ShortMessage.NOTE_ON : ShortMessage.NOTE_OFF, 0, note, 93);
            mSynthRcvr.send(mMsg, -1); // -1 means no time stamp
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        mWin.setVisible(true);
    }

    public SongView getSongView() {
        return mWin.getSongView();
    }

    public boolean isKeyVisible(int midiNote) {
        return mWin.getKeyboard().isKeyVisible(midiNote);
    }

    public void setScore(int score) {
        mWin.setScore(score);
    }

}
