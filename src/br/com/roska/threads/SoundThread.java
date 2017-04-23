package br.com.roska.threads;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import br.com.roska.images.Reference;
import br.com.roska.main.Painter;
import br.com.roska.util.SoundLoader;

public class SoundThread implements Runnable {

	public static final int REPEAT = 1;
	public static final int NOREPEAT = 0;
	private String ind;
	private boolean repeat;
	private int stop;
	public boolean cancel;
	Clip clip;

	public SoundThread(String ref, int rep, int sec) {
		this.ind = ref;
		this.repeat = rep == 1;
		this.stop = sec;
	}

	public void cancel() {
		clip.stop();
	}

	public void run() {
		try {

			SoundLoader<Reference> soundL = new SoundLoader<Reference>(new Reference());
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundL.getPath("sounds/" + ind));

			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();

			while (repeat) {
				Thread.sleep(stop * 1000);

				ais = AudioSystem.getAudioInputStream(soundL.getPath("sounds/" + ind));

				clip = AudioSystem.getClip();
				clip.open(ais);
				clip.start();
				if (cancel) {
					clip.stop();
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Painter.logger.log(e);
		}
	}

}