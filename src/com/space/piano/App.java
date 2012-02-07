package com.space.piano;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

public class App {

    private MainWindow mWin;
    private Synthesizer mSynth;
    private ShortMessage mMsg;
    private Receiver mSynthRcvr;
    private Game mGame;
    private MidiDevice mMidiDev;
    private Transmitter mMidiTrans;
    private SongLibrary mSongLib;

    public App(String[] args) {

        try {
            // Initialize midi
            mSynth = MidiSystem.getSynthesizer();
            mSynth.open();
            mMsg = new ShortMessage();
            mSynthRcvr = mSynth.getReceiver();

            // Load tutorial songs
            mSongLib = new SongLibrary("/list");

            // Detect Midi keyboard
            try {
                Info[] infos = MidiSystem.getMidiDeviceInfo();
                for (Info info : infos) {
                    MidiDevice device = MidiSystem.getMidiDevice(info);
                    if ("USB MIDI Device".equals(info.getName())) {
                        if (device.getMaxTransmitters() != 0) {
                            System.out.println("Found MIDI device: " + info);
                            mMidiDev = device;
                        }
                    }
                }
                if (mMidiDev != null) {
                    mMidiDev.open();
                    mMidiTrans = mMidiDev.getTransmitter();
                    mMidiTrans.setReceiver(new MidiReceiver(this));
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }

            // Create window
            mWin = new MainWindow(this);

            // Start game
            mGame = new EasyGame(this);
            mGame.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onKey(int note, boolean on) {
        play(note, on);
        mGame.onKey(note, on);
        mWin.getKeyboard().onKey(note, on);
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

    public void restart() {
        if (mGame != null) {
            mGame.restart();
        }
    }

    public SongLibrary getSongLib() {
        return mSongLib;
    }

}
