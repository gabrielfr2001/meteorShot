package br.com.roska.model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.roska.main.Driver;
import br.com.roska.main.Painter;
import br.com.roska.threads.SoundThread;
import br.com.roska.threads.Ticker;

public class Boss {

	private static final double VELY = 0.05;// 0.03
	private static final long TIME_ROTATING_LEFT = 180;
	private static final long MULTIPLIER = 360 / TIME_ROTATING_LEFT;
	private static final long MINIMIUM_ADD = 350;
	private final static int HEALTH = 10000;
	private static final int HEALTH_LOSS = 1000;
	private static final int EACH_HEART = 1000;
	public double x;
	public double y;
	public Image IMAGE;
	public double vx;
	public double vy;
	public long tick;
	public double rotation;
	private Random r;
	private boolean rotationLeft;
	private boolean onceRotationLeft;
	private long rotationTickLeft;
	private boolean rigt;
	private boolean rigtrigt;
	private boolean last;
	private boolean onceEver;
	private long lastAdd;
	private int health;
	private boolean onceAdd;
	private boolean died;
	private boolean dieOnce;

	public Boss() {
		this.r = new Random();
		onceEver = true;
		Painter.playSound("boss_battle0.wav", SoundThread.NOREPEAT, 0);

		if (Painter.mainThread != null) {
			Painter.mainThread.cancel();
		}

		health = HEALTH;
	}

	public void tick() {
		y += vy;
		x += vx;
		vy = health > 3000 ? VELY : 4 * VELY;

		int state = health > 3000 ? 150 : 50;

		if (!died) {

			if (y + IMAGE.getHeight(null) > Painter.height - 240) {
				Ticker.doGameOver();
			}
			if (r.nextInt(state) == 1) {
				if (rotationLeft == false) {

					rotationLeft = true;
					onceRotationLeft = true;

					if (onceEver) {
						rigtrigt = true;
						onceEver = false;
					} else {
						rigtrigt = false;
					}

					rigt = !last;
					last = rigt;

				}
			}
			if (tick - lastAdd > MINIMIUM_ADD && !onceAdd) {
				onceAdd = true;
				add(false);
			}

			rotationLeft();

			if (health <= 0) {

				for (int i = 0; i < Painter.meteors.size(); i++) {
					if (Painter.meteors.get(i).size == 100) {
						Painter.meteors.remove(Painter.meteors.get(i));
					}
				}

				if (Painter.mainThread != null) {
					Painter.mainThread.cancel();
				}
				
				Painter.playSound(Driver.MAIN_MUSIC, SoundThread.REPEAT, Driver.MAIN_MUSIC_LOOP_TIME);

				Ticker.onBossBattle = false;
				died = true;
				if (!dieOnce) {
					dieOnce = true;
					Painter.addPontos += 10000;
				}
			}
		} else {
			vy = -VELY * 10;
		}
		tick++;

	}

	private void rotationLeft() {
		if (rotationLeft) {

			if (onceRotationLeft) {
				rotationTickLeft = tick;
				onceRotationLeft = false;
			}

			rotation += Math.sin(Math.toRadians((tick - rotationTickLeft) * MULTIPLIER + (rigt ? 180 : 0))) * 0.2f;
			double angle = Math.sin(Math.toRadians((tick - rotationTickLeft) * MULTIPLIER + (rigt ? 180 : 0))) * 0.04f;

			if (angle < 0 && rigt) {
				vx += (!rigtrigt ? 2 : 1) * angle;
			} else if (angle > 0 && !rigt) {
				vx += (!rigtrigt ? 2 : 1) * angle;
			}
			if (tick > rotationTickLeft + TIME_ROTATING_LEFT) {
				rotationLeft = false;
				vx = 0;
			}

		}

	}

	public void paint(Graphics2D g) {
		if (IMAGE != null) {
			AffineTransform transform = g.getTransform();
			g.rotate(Math.toRadians(rotation), (int) x - IMAGE.getWidth(null) / 2 + IMAGE.getWidth(null) / 2,
					(int) y + IMAGE.getHeight(null) / 2);

			g.drawImage(IMAGE, (int) x - IMAGE.getWidth(null) / 2, (int) y, null);

			g.setTransform(transform);
		}

		for (int i = 0; i < health / EACH_HEART; i++) {
			g.drawImage(Painter.HEART, (int) x + IMAGE.getWidth(null) / (HEALTH / EACH_HEART) * i
					- IMAGE.getWidth(null) / 2 + Painter.HEART.getWidth(null) / 2, (int) y + IMAGE.getHeight(null),
					null);
		}

	}

	public void add(boolean loss) {
		if (!died) {
			if (loss)
				health -= HEALTH_LOSS;

			lastAdd = tick;
			String statement = "";

			int n1 = 0, n2 = 0;

			if (Painter.adultMode) {
				n1 = r.nextInt(Painter.level + 1) + 1;
				n2 = r.nextInt(Painter.level + 1) + 1;
			} else {
				int value = r.nextInt(10) + 1;
				n1 = value > 10 ? 10 : value;
				value = r.nextInt(10) + 1;
				n2 = value > 10 ? 10 : value;
			}

			statement = n1 + " x " + n2;

			int size = (int) 100;
			Meteor meteor = null;

			for (;;) {
				try {
					meteor = new Meteor(x, y, statement, size);
					break;
				} catch (Exception e) {
					e.printStackTrace();
					Painter.logger.log(e);
				}
			}
			meteor.n1 = n1;
			meteor.n2 = n2;

			Painter.imageUtil.setImage(Meteor.IMAGE);
			meteor.image = Painter.imageUtil.resize(size, size);

			meteor.speed = (Math.random() * 0.2 + 0.3);
			meteor.vy = -1;
			meteor.appendRotation = Math.random() - Math.random();
			Painter.add = false;

			List<Meteor> meteors = new ArrayList<Meteor>();

			meteors.add(meteor);

			for (int i = 0; i < Painter.meteors.size(); i++) {
				Meteor m = Painter.meteors.get(i);
				meteors.add(m);
			}

			Painter.meteors.add(meteor);
			Painter.actualize = true;
			Ticker.atualize = true;
			Ticker.refactoryMeteor();
		}
	}
}
