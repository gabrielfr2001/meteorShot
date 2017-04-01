package ds;

import java.awt.Color;
import java.awt.Graphics2D;

public class Particle {
	Color c;
	double x;
	double y;
	double vx;
	double vy;
	double spreadness;
	double life;
	boolean active;
	double size;
	double sizeDecay;

	public void tick() {
		if (active) {
			x += vx;
			y += vy;
			x += spreadness * Math.random() - spreadness * Math.random();
			y += spreadness * Math.random() - spreadness * Math.random();
			life--;
			size -= sizeDecay;
			if (life == 0) {
				active = false;
			}
		}
	}

	public void paint(Graphics2D g) {
		g.setColor(c);
		g.fillRect((int) x, (int) y, (int) size, (int) size);
	}
}
