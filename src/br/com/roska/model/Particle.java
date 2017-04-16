package br.com.roska.model;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Particle {
	public Color c;
	public double x;
	public double y;
	public double vx;
	public double vy;
	public double spreadness;
	public double rotation;
	public double rotateness;
	public double life;
	public boolean active;
	public double size;
	public double sizeDecay;
	public Image image;
	private float opacity = 1;
	public double alphaReduce;

	public void tick() {
		if (active) {
			x += vx;
			y += vy;
			x += spreadness * Math.random() - spreadness * Math.random();
			y += spreadness * Math.random() - spreadness * Math.random();
			life--;
			opacity -= alphaReduce;
			if (opacity <= 0) {
				opacity = 0;
			}
			rotation += rotateness;
			size -= sizeDecay;
			if (life == 0) {
				active = false;
			}
		}
	}

	public void paint(Graphics2D g) {
		if (image == null) {
			g.setColor(c);
			g.fillRect((int) x, (int) y, (int) size, (int) size);
		} else {
			AffineTransform transform = g.getTransform();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			g.rotate(Math.toRadians(rotation), (int) x + image.getWidth(null) / 2, (int) y + image.getHeight(null) / 2);
			g.drawImage(image, (int) x, (int) y, null);
			g.setTransform(transform);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}

	}
}
