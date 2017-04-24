package br.com.roska.threads;

import java.awt.Color;
import java.util.List;

import br.com.roska.main.Painter;
import br.com.roska.model.Meteor;
import br.com.roska.model.Particle;
import br.com.roska.util.ImageUtil;

public class Threader implements Runnable {
	public static final int EXPLODE = 0;
	public static final int KILL = 1;
	private Meteor m;
	private int i;
	public int TYPE;
	private ImageUtil imageUtil;

	public Threader(Meteor m, int i) {
		this.m = m;
		this.i = i;

		imageUtil = new ImageUtil();
	}

	@Override
	public void run() {
		List<Meteor> meteors = Painter.meteors;

		meteors.remove(m);
		meteors.add(m);

		Painter.meteors = meteors;

		if (Ticker.onBossBattle) {
			if (Ticker.boss != null) {
				Ticker.boss.add(true);
			}
		}
		if (Painter.meteors.size() > 0) {
			Ticker.atualize = true;
		}

		if (i == KILL) {
			m.destroyType = TYPE;

			Painter.cando = true;
			Painter.add = true;
			Painter.meteorDestroy = m;

			if (TYPE == 0) {
				m.inactive = true;
				m.dead = true;
				m.canadParticle = false;
				for (int i = 0; i < m.particles.size(); i++) {
					if (m.particles.get(i) != null) {
						m.particles.get(i).sizeDecay = 0.3;
					}
				}
				for (int i = 0; i < 30; i++) {
					m.vely += m.accy;
					m.y -= m.vely;
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
						Painter.logger.log(e);
					}

				}
			} else if (TYPE == 1) {
				m.inactive = true;
				m.notMoving = true;

				imageUtil.setImage(m.image);

				m.image = imageUtil.dye(new Color(0, 70, 255, 100));

				m.canadParticle = false;
				m.particleColor = new Color(0, 70, 255, 100);
				for (int i = 0; i < m.particles.size(); i++) {
					if (m.particles.get(i) != null) {
						m.particles.get(i).sizeDecay = 0.8;
					}
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Painter.logger.log(e);
				}

				m.dead = true;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Painter.logger.log(e);
				}

			} else {
				m.inactive = true;
				m.notMoving = true;
				m.appendRotation = 2;
				m.y++;
				for (int o = 0; o < m.particles.size(); o++) {
					if (m.particles.get(o) != null) {
						m.particles.get(o).sizeDecay = 1;
						m.particles.get(o).life = 3;
					}
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Painter.logger.log(e);
				}

				for (int i = 0; i < 150; i++) {
					try {
						m.y += -3;
						m.x += 10;

						imageUtil.setImage(m.image);

						m.image = imageUtil.resize((int) (m.image.getWidth(null) * 0.95 + 1),
								(int) (m.image.getHeight(null) * 0.95) + 1);

						Thread.sleep(10);

						m.size = m.image.getHeight(null);
						for (int o = 0; o < m.particles.size(); o++) {
							if (m.particles.size() > 0) {
								Particle p = m.particles.get(o);
								if (p != null) {
									p.alphaReduce = 0.1;
									p.sizeDecay = 1;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						Painter.logger.log(e);
					}
				}
				m.dontExplode = true;
				m.dead = true;

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Painter.logger.log(e);
				}
				Painter.meteors.remove(m);

			}
		}
		if (i == EXPLODE) {
			m.dead = true;
			Painter.meteors.remove(m);
			Painter.add = true;
		}
		Painter.meteors.remove(m);
		Ticker.removeThreaders.add(this);
	}

}
