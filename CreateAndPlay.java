import java.applet.Applet;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.lang.IndexOutOfBoundsException;

import javax.sound.midi.Sequence;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.InvalidMidiDataException;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;


public class CreateAndPlay extends Applet {
	private static final int VELOCITY = 48;  // default volume
	private static final int ACCENT = 100;

	private static Sequencer sequencer = null;
	private static Synthesizer synthesizer = null;

	private static final int CLAVE = 75;
	private static final int BELL = 56;
	private static final int CONGA_LH = 63;  // LH hitting it
	private static final int CONGA_LT = 63;  // LH tap (heel on drum, just fingers hitting it)
	private static final int CONGA_RO = 63;  // RH open
	private static final int CONGA_RS = 62;  // RH slap
	private static final int STICK = 42;  // for cascara
	private static final int GUIRO_L = 74;  // long
	private static final int GUIRO_S = 73;  // short
	private static final int INTRO = 42;
	private static final int BONGO_RS = 60;  // high bongo - doesn't sound good....
	private static final int BONGO_LT = 60;
	private static final int BONGO_LH = 60;
	private static final int BONGO_RO = 61;  // low bongo

	private static int introOffset = 0;

	private static ArrayList tracks = new ArrayList();

	public void init() {
		System.out.println("init");
		create();
	}

	public static void main(String[] args) {
	}

	public static void create()	throws RuntimeException {
		System.out.println("create");

		try {
			Sequence sequence = null;
			sequence = new Sequence(Sequence.PPQ, 2);  // so we are saying that there are 2 midi ticks per beat

			Track intro = sequence.createTrack();
			tracks.add("intro");

//			introOffset = 0;  // setting this shifts all other drumEvents forward by this amount

			drumEvent(intro, INTRO, 0);
			drumEvent(intro, INTRO, 4);
			drumEvent(intro, INTRO, 8);
			drumEvent(intro, INTRO, 10);
			drumEvent(intro, INTRO, 12);
			drumEvent(intro, INTRO, 14);

			introOffset = 16;  // setting this shifts all other drumEvents forward by this amount

			Track clave = sequence.createTrack();
			tracks.add("clave");

			drumEvent(clave, CLAVE, 0, 127);
			drumEvent(clave, CLAVE, 3, 127);
			drumEvent(clave, CLAVE, 6, 127);
			drumEvent(clave, CLAVE, 10, 127);
			drumEvent(clave, CLAVE, 12, 127);
			drumEvent(clave, CLAVE, 15, 0);  // add this one to pad out the track to the full length
		
// 		MetaMessage tempoMsg = new MetaMessage();
// 		// (60000000 / 300) gives microseconds per quarter note.  Change this to hex gives 0x30d40 which we put in the last 3 bytes.
// 		tempoMsg.setMessage(type, new byte[]{0xff, 0x51, 0x03, 0x03, 0x0d, 0x40}, 6);
// 		MidiEvent tempoEvent = new MidiEvent(tempoMsg, 0);
// 		clave.add(tempoEvent);

			Track bellS = sequence.createTrack();
			tracks.add("bellS");

			drumEvent(bellS, BELL, 0);
			drumEvent(bellS, BELL, 4);
			drumEvent(bellS, BELL, 8);
			drumEvent(bellS, BELL, 12);
			drumEvent(bellS, BELL, 15, 0);

			Track bell = sequence.createTrack();
			tracks.add("bell");

			drumEvent(bell, BELL, 0, 2, ACCENT);
			drumEvent(bell, BELL, 2);
			drumEvent(bell, BELL, 3);
			drumEvent(bell, BELL, 4, 6, ACCENT);
			drumEvent(bell, BELL, 6);
			drumEvent(bell, BELL, 7);
			drumEvent(bell, BELL, 8, 10, ACCENT);
			drumEvent(bell, BELL, 10, 12, VELOCITY);
			drumEvent(bell, BELL, 12, 14, ACCENT);
			drumEvent(bell, BELL, 14);
			drumEvent(bell, BELL, 15);

			Track conga = sequence.createTrack();
			tracks.add("conga");

			drumEvent(conga, CONGA_LH, 0, 43);
			drumEvent(conga, CONGA_LT, 1, 54);
			drumEvent(conga, CONGA_RS, 2, 101);
			drumEvent(conga, CONGA_LT, 3, 50);
			drumEvent(conga, CONGA_LH, 4, 42);
			drumEvent(conga, CONGA_LT, 5, 51);
			drumEvent(conga, CONGA_RO, 6, 100);
			drumEvent(conga, CONGA_LT, 7, 54);
			drumEvent(conga, CONGA_LH, 8, 42);
			drumEvent(conga, CONGA_LT, 9, 56);
			drumEvent(conga, CONGA_RS, 10, 101);
			drumEvent(conga, CONGA_LT, 11, 51);
			drumEvent(conga, CONGA_LH, 12, 42);
			drumEvent(conga, CONGA_LT, 13, 51);
			drumEvent(conga, CONGA_RO, 14, 100);
			drumEvent(conga, CONGA_RO, 15, 100);

			Track stick = sequence.createTrack();  // track 3
			tracks.add("stick");

			drumEvent(stick, STICK, 0, 80);
			drumEvent(stick, STICK, 2, 80);
			drumEvent(stick, STICK, 3, 80);
			drumEvent(stick, STICK, 5, 80);
			drumEvent(stick, STICK, 7, 80);
			drumEvent(stick, STICK, 8, 80);
			drumEvent(stick, STICK, 10, 80);
			drumEvent(stick, STICK, 12, 80);
			drumEvent(stick, STICK, 13, 80);
			drumEvent(stick, STICK, 15, 80);

			Track congaS = sequence.createTrack();
			tracks.add("congaS");
			
			drumEvent(congaS, CONGA_RS, 2, 101);
			drumEvent(congaS, CONGA_RO, 6, 100);
			drumEvent(congaS, CONGA_RO, 7, 100);
			drumEvent(congaS, CONGA_RS, 10, 101);
			drumEvent(congaS, CONGA_RO, 14, 100);
			drumEvent(congaS, CONGA_RO, 15, 100);

			Track guiro = sequence.createTrack();
			tracks.add("guiro");

			drumEvent(guiro, GUIRO_L, 0, 2, VELOCITY);
			drumEvent(guiro, GUIRO_S, 2);
			drumEvent(guiro, GUIRO_S, 3);
			drumEvent(guiro, GUIRO_L, 4, 6, VELOCITY);
			drumEvent(guiro, GUIRO_S, 6);
			drumEvent(guiro, GUIRO_S, 7);
			drumEvent(guiro, GUIRO_L, 8, 10, VELOCITY);
			drumEvent(guiro, GUIRO_S, 10);
			drumEvent(guiro, GUIRO_S, 11);
			drumEvent(guiro, GUIRO_L, 12, 14, VELOCITY);
			drumEvent(guiro, GUIRO_S, 14);
			drumEvent(guiro, GUIRO_S, 15);

// 			Track bongo = sequence.createTrack();
// 			tracks.add("bongo");

// 			drumEvent(bongo, BONGO_RS, 0, 100);
// 			drumEvent(bongo, BONGO_LT, 1, 40);
// 			drumEvent(bongo, BONGO_RS, 2, 54);
// 			drumEvent(bongo, BONGO_LH, 3, 40);
// 			drumEvent(bongo, BONGO_RS, 4, 100);
// 			drumEvent(bongo, BONGO_LT, 5, 40);
// 			drumEvent(bongo, BONGO_RO, 6, 100);
// 			drumEvent(bongo, BONGO_LH, 7, 40);
// 			drumEvent(bongo, BONGO_RS, 8, 100);  // repeat
// 			drumEvent(bongo, BONGO_LT, 9, 40);
// 			drumEvent(bongo, BONGO_RS, 10, 54);
// 			drumEvent(bongo, BONGO_LH, 11, 40);
// 			drumEvent(bongo, BONGO_RS, 12, 100);
// 			drumEvent(bongo, BONGO_LT, 13, 40);
// 			drumEvent(bongo, BONGO_RO, 14, 100);
// 			drumEvent(bongo, BONGO_LH, 15, 40);

			sequencer = MidiSystem.getSequencer();
			System.out.println(sequencer.getClass());

			if (sequencer == null) {
				System.out.println("No Sequencer available");
				System.exit(1);
			}

			sequencer.addMetaEventListener(new MetaEventListener() {
					public void meta(MetaMessage event) {
						if (event.getType() == 47) {  // end of track
							sequencer.close();
							if (synthesizer != null) {
								synthesizer.close();
							}
							System.exit(0);
						}
					}
				});

			sequencer.open();
			sequencer.setSequence(sequence);

			if (!(sequencer instanceof Synthesizer)) {
				synthesizer = MidiSystem.getSynthesizer();
				synthesizer.open();
				Receiver synthReceiver = synthesizer.getReceiver();
				Transmitter	seqTransmitter = sequencer.getTransmitter();
				seqTransmitter.setReceiver(synthReceiver);
			}
		
			setTempo(120);
			sequencer.setLoopStartPoint(introOffset);
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);

		} catch (MidiUnavailableException e) {
			throw new RuntimeException(e);
		} catch (InvalidMidiDataException e) {
			throw new RuntimeException(e);
		}
	}

	public static void playSeq() {
		System.out.println("play");
		sequencer.start();
	}

	public static void playSeq(String instrument) {
		System.out.println("play " + instrument);
		resetSeq();
		for (int i = 0; i < tracks.size(); i++) {
			sequencer.setTrackMute(i, true);
		}
		int t = getTrackNumFromName(instrument);
		sequencer.setTrackMute(t, false);
 		t = getTrackNumFromName("intro");
 		sequencer.setTrackMute(t, false);
		
		playSeq();
	}

	public static void stopSeq() {
		System.out.println("stop");
		sequencer.stop();
	}

	public static void resetSeq() {
		System.out.println("reset");
		stopSeq();
		sequencer.setTickPosition(0);
	}

	public static void setTempo(int tempo) {
		//sequencer.setTempoInBPM(tempo);  can't use this as there is a bug in Sun's libraries that causes the tempo to reset to 120bpm
		System.out.println("tempo: " + tempo);
		float current = sequencer.getTempoInBPM();
		float factor = tempo / current;
		sequencer.setTempoFactor(factor);
	}

	public static void getTempo() {
		System.out.println("tempo: " + sequencer.getTempoInBPM() + " tempoFactor: " + sequencer.getTempoFactor());
	}

	private static int getTrackNumFromName(String name) {
		int track = tracks.indexOf(name);
		if (track == -1) {
			throw new IndexOutOfBoundsException("No track for '" + name + "'");
		}
		return track;
	}

	public static void setMute(String instrument, boolean mute) {
		System.out.println("mute: " + instrument + " " + mute);
		int t = getTrackNumFromName(instrument);
		sequencer.setTrackMute(t, mute);
	}

	public static void toggle(String instrument) {
		System.out.println("toggle mute: " + instrument);
		int t = getTrackNumFromName(instrument);
		boolean state = sequencer.getTrackMute(t);
		sequencer.setTrackMute(t, !state);
		if (sequencer.getTrackMute(t) == state) {
			System.out.println("Failed to mute");
		}
	}

	private static void drumEvent(Track track, int drum, long tick, long stop, int velocity) throws RuntimeException {
		try {
			ShortMessage message;
			MidiEvent event;
			
			message	= new ShortMessage();
			message.setMessage(ShortMessage.NOTE_ON, 9, drum, velocity);
			event = new MidiEvent(message, tick + introOffset);
			track.add(event);
			
			message	= new ShortMessage();
			message.setMessage(ShortMessage.NOTE_OFF, 9, drum, 0);
			event = new MidiEvent(message, stop + introOffset);
			track.add(event);		
		} catch (InvalidMidiDataException e) {
			throw new RuntimeException(e);
		}
	}
		
	private static void drumEvent(Track track, int drum, long tick) {
		drumEvent(track, drum, tick, VELOCITY);
	}

	private static void drumEvent(Track track, int drum, long tick, int velocity) {
		drumEvent(track, drum, tick, tick + 1, velocity);
	}

	private static void out(String strMessage)
	{
		System.out.println(strMessage);
	}
}
