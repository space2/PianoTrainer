package com.space.piano;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

public class SongLibrary {

    private Vector<Song> mSongs = new Vector<Song>();

    public SongLibrary(String listRes) {
        InputStream is = getClass().getResourceAsStream(listRes);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while (null != (line = br.readLine())) {
                if (line.endsWith("\r")) {
                    line = line.substring(0, line.length() - 1);
                }
                Sequence seq = MidiSystem.getSequence(getClass().getResourceAsStream("/" + line));
                Song song = new Song(seq);
                mSongs.add(song);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return mSongs.size();
    }

    public Song get(int idx) {
        return mSongs.get(idx);
    }

}
