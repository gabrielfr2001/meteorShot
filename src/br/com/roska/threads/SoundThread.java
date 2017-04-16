package br.com.roska.threads;

import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import br.com.roska.images.Reference;
import br.com.roska.main.Painter;
import br.com.roska.util.SoundLoader;

public class SoundThread implements Runnable {

	private static final int MIN = 1;
	private static final int MAX = 2;

	public SoundThread() {
	}

	public void run() {
		try {

			SoundLoader<Reference> soundL = new SoundLoader<Reference>(new Reference());

			AudioInputStream ais = AudioSystem.getAudioInputStream(
					soundL.getPath("sounds/shot_" + (new Random().nextInt(MAX - MIN + 1) + MIN) + ".wav"));

			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();

		} catch (Exception e) {
			e.printStackTrace();
			Painter.logger.log(e);
		}
	}

}