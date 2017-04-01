import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Painter extends JPanel {

	private static List<Particle> particles = new ArrayList<Particle>();
	private static final long serialVersionUID = 6105978439274810638L;
	private int frames;
	private List<Rectangle> blocks = new ArrayList<>();

	public Painter() {
		blocks.add(new Rectangle(100, 100, 300, 300));
		Random r = new Random();
		for (int i = 0; i < 500; i++) {
			Particle p = new Particle();
			BufferedImage bufferedImage;
			try {
				bufferedImage = ImageIO
						.read(Files.newInputStream(Paths.get("cabeça_" + (int) (r.nextInt(4) + 1) + ".png")));
				Image img = (Image) bufferedImage;
				p.img = img;

				bufferedImage = ImageIO
						.read(Files.newInputStream(Paths.get("calça_" + (int) (r.nextInt(4) + 1) + ".png")));
				img = (Image) bufferedImage;
				p.img3 = img;

				bufferedImage = ImageIO
						.read(Files.newInputStream(Paths.get("camisa_" + (int) (r.nextInt(4) + 1) + ".png")));
				img = (Image) bufferedImage;
				p.img2 = img;

			} catch (IOException e) {
				e.printStackTrace();
			}
			particles.add(p);
		}

	}

	public void paint(Graphics g) {

		Graphics2D p = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		p.setRenderingHints(rh);

		p.setColor(Color.WHITE);
		p.fillRect(0, 0, 500, 500);
		p.setColor(Color.BLACK);
		p.drawString(Integer.toString(frames), 0, 10);

		for (int a = 0; a < particles.size(); a++) {

			Particle x = particles.get(a);

			if (Math.random() > 0.9) {
				x.state = Particle.globalState;
			}

			double multiplicador1 = 0.8;

			if (x.state == 2) {
				multiplicador1 = 0.3;
			}

			if (x.state != 1) {

				for (int b = 0; b < particles.size(); b++) {

					Particle y = particles.get(b);

					double hip = Math.sqrt((x.x - y.x) * (x.x - y.x) + (x.y - y.y) * (x.y - y.y));

					double ex = (x.x - y.x);
					double ey = (x.y - y.y);

					if (hip < x.size) {
						x.x += ex / (hip * multiplicador1 + 0.001);
						x.y += ey / (hip * multiplicador1 + 0.001);
					}

					if (hip < x.size / 2) {
						x.x += ex / (hip + 0.0000001);
						x.y += ey / (hip + 0.0000001);
					}

					if (hip < x.size * 1.0f) {
						x.vy /= 1.08;
					}

				}

				x.x += (Math.random() - Math.random()) / (125f * Math.pow(multiplicador1, 4));
				x.y += (Math.random() - Math.random()) / (125f * Math.pow(multiplicador1, 4));

				if (x.state != 2) {
					x.vy += Particle.ACC;
				} else {
					x.vy -= Particle.ACC / 3;
				}
				if (x.vy > 5) {
					x.vy = 5;
				}
				if (x.vy < -5) {
					x.vy = -5;
				}

				x.y += x.vy;

				for (Rectangle r : blocks) {
					if (x.x > r.x + r.width) {
						x.x = r.x + r.width;
					}
					if (x.x < r.x) {
						x.x = r.x;
					}
					if (x.y > r.y + r.height) {
						x.y = r.y + r.height;
					}
					if (x.y < r.y) {
						x.y = r.y;
					}
				}
			}
		}

		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).draw(p);
		}

		frames++;
		if (frames > 200) {
			Particle.globalState = 1;
		}
		if (frames > 300) {
			Particle.globalState = 2;
		}

		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();
	}
}
