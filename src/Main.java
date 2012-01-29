import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import com.space.piano.App;


public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
//        try {
//            Synthesizer synth = MidiSystem.getSynthesizer();
//            synth.open();
//
//            ShortMessage myMsg = new ShortMessage();
//            // Play the note Middle C (60) moderately loud
//            // (velocity = 93)on channel 4 (zero-based).
//            myMsg.setMessage(ShortMessage.NOTE_ON, 4, 60, 93);
//
//            Receiver synthRcvr = synth.getReceiver();
//            synthRcvr.send(myMsg, -1); // -1 means no time stamp
//
//            Thread.sleep(100);
//            myMsg.setMessage(ShortMessage.NOTE_OFF, 4, 60, 93);
//            synthRcvr.send(myMsg, -1); // -1 means no time stamp
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        new App(args).run();

    }

}
