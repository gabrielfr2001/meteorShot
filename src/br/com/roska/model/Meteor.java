package br.com.roska.model;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.roska.main.Painter;
import br.com.roska.util.ImageUtil;

public class Meteor {
	public static Image IMAGE;
	public static Image DESTROCO_0;
	public static Image DESTROCO_1;
	public static Image PARTICLE;
	public Image image;
	public double x;
	public double y;
	public double size;
	public double accy = 2;
	public double vely;
	public String statement;
	public boolean first = false;
	public double speed;
	public int n1;
	public int n2;
	public List<Particle> particles = new ArrayList<>();
	public double vx;
	public double vy;
	public boolean canadParticle = true;
	public boolean dead;
	public int destroyType;
	private boolean firstDestroy;
	private double rotation;
	public double appendRotation;
	public boolean notMoving;
	public Color particleColor = new Color(255, 165, 0);
	public boolean dontExplode;
	private ImageUtil imageUtil;
	public boolean inactive;
	public int targetX;
	public int targetY;
	public int life;
	public boolean bossActive;

	public Meteor(double x, double y, String stat, double size) {
		this.x = x;
		this.y = y;
		this.statement = stat;
		this.size = size;

		imageUtil = new ImageUtil();
	}

	public void paint(Graphics2D g) {
		if (!dead) {

			FontMetrics metrics = g.getFontMetrics(Painter.font);
			int xx = (int) ((size - metrics.stringWidth(statement)) / 2);
			int yy = (int) (((size - metrics.getHeight()) / 2) + metrics.getAscent());

			if (!first) {
				first = true;
			}

			for (int i = 0; i < particles.size(); i++) {
				Particle o = particles.get(i);
				if (o != null)
					if (!o.active) {
						particles.remove(o);
						Painter.particles--;
					} else {
						o.paint(g);
					}
			}

			g.setColor(Color.WHITE);
			AffineTransform transform = g.getTransform();

			g.rotate(Math.toRadians(rotation), (int) x + image.getWidth(null), (int) y + image.getHeight(null));
			rotation += appendRotation;
			g.drawImage(image, (int) (x + size / 2), (int) (y + size / 2), null);
			g.setTransform(transform);
			g.drawString(statement, (int) (x + xx + size / 2), (int) (y + yy + size / 2));

		} else {
			if (!dontExplode) {
				if (!firstDestroy) {
					Random random = new Random();
					for (int i = 0; i < random.nextInt(20) + 100; i++) {
						Particle p = new Particle();

						imageUtil.setImage(DESTROCO_0);

						p.image = imageUtil.transform(random.nextInt(10) + 10, random.nextInt(10) + 10,
								(destroyType == 1 || destroyType == 0) ? Color.WHITE : Color.BLUE);

						double angle = Math.random() * 360;
						p.vy = Math.sin(angle) * Math.random() * 2 + vy;
						p.vx = Math.cos(angle) * Math.random() * 2 + vx;
						p.active = true;
						p.life = 200;
						p.alphaReduce = 0.01;
						p.image = Meteor.DESTROCO_0;
						p.x = x + size;
						p.rotateness = Math.random() - Math.random();
						p.y = y + size;
						particles.add(p);
					}
					firstDestroy = true;
				}
				for (int i = 0; i < particles.size(); i++) {
					Particle o = particles.get(i);
					if (o != null) {
						if (!o.active) {
							particles.remove(o);
						} else {
							o.paint(g);
						}
					}
				}
			}
		}

	}
}
