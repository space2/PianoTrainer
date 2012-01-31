package com.space.piano;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;


public class App {

    private MainWindow mWin;
    private Synthesizer mSynth;
    private ShortMessage mMsg;
    private Receiver mSynthRcvr;

    public App(String[] args) {
        mWin = new MainWindow(this);

        try {
            mSynth = MidiSystem.getSynthesizer();
            mSynth.open();
            mMsg = new ShortMessage();
            mSynthRcvr = mSynth.getReceiver();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Sequence seq = MidiSystem.getSequence(getClass().getResourceAsStream("/JingleBells.mid"));
            Song song = new Song(seq);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

}
