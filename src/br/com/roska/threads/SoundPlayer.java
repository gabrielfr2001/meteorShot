package br.com.roska.threads;

import br.com.roska.main.Painter;

public class SoundPlayer implements Runnable {

	private String string;
	private int repeat;
	private int time;

	public SoundPlayer(String string, int repeat, int time) {
		this.string = string;
		this.repeat = repeat;
		this.time = time;
	}

	@Override
	public void run() {
		SoundThread so = new SoundThread(string, repeat, time);
		Thread t = new Thread(so);
		t.start();
		Painter.threads.add(t);
		if (string.equals("arcade_music.wav") || string.equals("boss_battle0.wav")) {
			Painter.soundIndex = Painter.threads.indexOf(t);
			Painter.mainThread = so;
		}
	}

}
