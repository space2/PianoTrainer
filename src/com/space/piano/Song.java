package com.space.piano;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Song {

    private static final int MIN_NOTE_GAP = 100; // min 100ms delay between notes

    private static final int MIN_NOTE_LEN = 100; // min 100ms duration for a note

    private Vector<Note> mNotes = new Vector<Note>();
    private HashMap<Integer, Vector<Note>> mNotesPerNote = new HashMap<Integer, Vector<Note>>();
    private int mLength;

    public Song(Sequence seq) {
        int usPerTick = (int) (seq.getMicrosecondLength() / seq.getTickLength());
        mLength = (int) (seq.getMicrosecondLength() / 1000);
        Track[] tracks = seq.getTracks();
        for (int i = 0; i < tracks.length; i++) {
            HashMap<Integer, Note> notesOn = new HashMap<Integer, Note>();
            Track track = tracks[i];
            int size = track.size();
            for (int j = 0; j < size; j++) {
                MidiEvent event = track.get(j);
                int tm = (int) (event.getTick() * usPerTick / 1000);
                MidiMessage msg = event.getMessage();
                if (msg instanceof ShortMessage) {
                    ShortMessage smsg = (ShortMessage) msg;
                    if (smsg.getCommand() == ShortMessage.NOTE_ON) {
                        int midiNote = smsg.getData1();
                        int velocity = smsg.getData2();
                        if (velocity == 0) {
                            // Note off
                            Note note = notesOn.get(midiNote);
                            if (note != null) {
                                note.setOffTime(tm);
                            }
                        } else {
                            // Note on
                            Note note = new Note(midiNote, tm, tm);
                            notesOn.put(midiNote, note);
                            mNotes.add(note);
                            Vector<Note> tmp = mNotesPerNote.get(midiNote);
                            if (tmp == null) {
                                tmp = new Vector<Note>();
                                mNotesPerNote.put(midiNote, tmp);
                            }
                            tmp.add(note);
                        }
                    }
                }
            }
        }
        sort();

        // Adjust node boundaries, so there will be a bit of gap between notes
        for (Vector<Note> notes : mNotesPerNote.values()) {
            int size = notes.size();
            for (int i = 0; i < size - 1; i++) {
                Note cur = notes.get(i);
                Note next = notes.get(i + 1);
                int delta = next.getOnTime() - cur.getOffTime();
                if (delta < MIN_NOTE_GAP) {
                    delta = MIN_NOTE_GAP;
                    int offTime = next.getOnTime() - delta;
                    int noteLen = offTime - cur.getOnTime();
                    if (noteLen < MIN_NOTE_LEN) {
                        cur.setOffTime((cur.getOnTime() + next.getOnTime()) / 2);
                    } else {
                        cur.setOffTime(offTime);
                    }
                }
            }
        }
    }

    public int getLengthMs() {
        return mLength;
    }

    public int getCount() {
        return mNotes.size();
    }

    public Note getNote(int idx) {
        return mNotes.get(idx);
    }

    private void sort() {
        Collections.sort(mNotes, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                if (o1.getOnTime() < o2.getOnTime()) {
                    return -1;
                }
                if (o1.getOnTime() > o2.getOnTime()) {
                    return +1;
                }
                return 0;
            }
        });
    }

}
