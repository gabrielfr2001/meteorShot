package ds;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Meteor {
	static Image IMAGE;
	Image image;
	double x;
	double y;
	double size;
	double accy = 2;
	double vely;
	String statement;
	boolean first = false;
	double speed;
	public int n1;
	public int n2;
	public List<Particle> particles = new ArrayList<>();
	public double vx;
	public double vy;
	public boolean canadParticle = true;

	public Meteor(double x, double y, String stat, double size) {
		this.x = x;
		this.y = y;
		this.statement = stat;
		this.size = size;
	}

	public void paint(Graphics2D g) {

		FontMetrics metrics = g.getFontMetrics(Painter.font);
		int xx = (int) ((size - metrics.stringWidth(statement)) / 2);
		int yy = (int) (((size - metrics.getHeight()) / 2) + metrics.getAscent());

		if (!first) {
			first = true;
		}

		if (particles.size() < 2000 && canadParticle) {
			for (int i = 0; i < 6; i++) {
				Particle p = new Particle();
				p.x = x + size - 5 + Math.random() * size / 3 - Math.random() * size / 3;
				p.y = y + size + Math.random() * size / 3 - Math.random() * size / 2;
				p.active = true;
				p.spreadness = 2;
				p.vy = -vy;
				p.vx = -vx;
				p.size = 30;
				p.sizeDecay = 0.1;
				p.c = new Color((int) (Math.random() * 100) + 100, (int) (Math.random() * 100), 0);
				p.life = 200;
				particles.add(p);
			}
		}
		for (int i = 0; i < particles.size(); i++) {
			Particle o = particles.get(i);
			if (!o.active) {
				particles.remove(o);
			} else {
				o.tick();
				o.paint(g);
			}
		}

		g.setColor(Color.WHITE);
		g.drawImage(image, (int) (x + size / 2), (int) (y + size / 2), null);
		g.drawString(statement, (int) (x + xx + size / 2), (int) (y + yy + size / 2));
	}
}
