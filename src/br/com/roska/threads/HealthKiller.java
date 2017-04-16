package br.com.roska.threads;

import br.com.roska.main.Painter;

public class HealthKiller implements Runnable {

	private int i;
	private double decrement;

	public HealthKiller(int i, int d) {
		this.i = i;
		this.decrement = d;
	}

	@Override
	public void run() {
		for (int o = 0; o < i / decrement; o++) {
			Painter.health -= decrement;
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Painter.logger.log(e);
			}
		}
	}

}
