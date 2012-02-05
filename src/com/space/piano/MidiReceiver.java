package com.space.piano;

import java.util.HashMap;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MidiReceiver implements Receiver {

    private App mApp;
    private HashMap<Integer, Integer> mNotesOn = new HashMap<Integer, Integer>();

    public MidiReceiver(App app) {
        mApp = app;
    }

    @Override
    public void close() {
        // NOP
    }

    @Override
    public void send(MidiMessage msg, long timeStamp) {
        if (msg instanceof ShortMessage) {
            ShortMessage smsg = (ShortMessage) msg;
            int ch = smsg.getChannel();
            if (smsg.getCommand() == ShortMessage.NOTE_ON) {
                int midiNote = smsg.getData1();
                mNotesOn.put(ch, midiNote);
                mApp.onKey(midiNote, true);
            } else if (smsg.getCommand() == ShortMessage.CONTROL_CHANGE) {
                if (smsg.getData1() == 123) { // "all notes off"
                    Integer midiNote = mNotesOn.get(ch);
                    if (midiNote != null) {
                        mNotesOn.remove(ch);
                        mApp.onKey(midiNote, false);
                    }
                }
            }
        }
    }

}
