package ds;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Painter extends JPanel implements KeyListener, Runnable {

	private static final long serialVersionUID = 4073472496026368671L;

	static List<Meteor> meteors = new ArrayList<>();

	private int frames;
	private String pressed = "";
	private String pressedDisp = "";
	private int countDown;
	private boolean upCountDown = false;
	private int countDown2;
	private int acertou;
	private int errou;
	private long pontos;
	private int n;
	private int level;
	private Image BACKGROUND;
	private Image LANDSCAPE;
	public static Font font = new Font("Tahoma", Font.BOLD, 35);
	private static Font font2 = new Font("Tahoma", Font.BOLD, 45);
	private List<Thread> threads = new ArrayList<>();
	private int levelUp;
	private int pontosUp;
	private int addPontos;
	private int multiplier = 19;
	private int addic;
	private double param = 2;
	private int index;
	private int choices = 5;
	private boolean actualize = true;
	private String[] ind;
	static boolean add = true;
	static double health;
	static boolean cando = true;
	private static int width;
	private static int height;

	public Painter(JFrame frame) {
		Meteor.IMAGE = loadImage("meteor.png");

		width = frame.getContentPane().getWidth() + 10;
		height = frame.getContentPane().getHeight() + 5;

		BACKGROUND = resize(toBufferedImage(loadImage("space.jpg")), width, height);
		System.out.println(BACKGROUND);

		LANDSCAPE = resize(toBufferedImage(loadImage("earth.png")), width, height);
		System.out.println(LANDSCAPE);

		health = 10000;
	}

	public void paint(Graphics g) {

		Graphics2D p = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		p.setRenderingHints(rh);

		p.setColor(Color.WHITE);
		paintBackground(p);
		paintLandscape(p);
		p.setColor(Color.BLACK);
		p.setFont(font);
		Random r = new Random();
		if (meteors.size() < 10 && add) {
			String statement = "";
			int n1 = r.nextInt(level + 1) + 1;
			int n2 = r.nextInt(level + 1) + 1;

			statement += n1;
			statement += " x ";
			statement += n2;

			int size = (int) 150;
			Meteor meteor = new Meteor(new Random().nextInt(width - size * 2), -size * 1.5, statement, size);
			meteor.n1 = n1;
			meteor.n2 = n2;
			meteor.image = resize(toBufferedImage(Meteor.IMAGE), size, size);
			meteor.speed = (Math.random() * 0.5 + 0.6);
			add = false;

			meteors.add(meteor);
			actualize = true;
		}
		if (!pressed.equals("") && meteors.size() > 0)
			if (cando)
				if (meteors.get(0).n1 * meteors.get(0).n2 == Integer.parseInt(pressed)) {
					Thread thread = new Thread(new Threader(meteors.get(0), Threader.KILL));
					threads.add(thread);
					thread.start();
					acertou = 10;
					pressedDisp = "";
					pressed = "";
					addPontos = (int) (height - 100 - meteors.get(0).y) * multiplier;

					int ponto = (int) (addPontos * param);
					int var = 0;

					while (ponto != 0) {
						ponto /= param;
						var += ponto;
					}

					addic += addPontos * param - var / 2;
					// pontos += addPontos;
					pontosUp += addPontos;
					cando = false;
					level++;
					levelUp = 50;
				}
		for (int i = 0; i < meteors.size(); i++) {
			Meteor m = meteors.get(i);
			if (m.y > height - 100) {
				Thread thread = new Thread(new Threader(m, Threader.EXPLODE));
				threads.add(thread);
				thread.start();
				errou = 10;
				pressedDisp = "";
				pressed = "";
				Thread t = new Thread(new HealthKiller(1000, 20));
				t.start();
				threads.add(t);
			}
			double angle = getAngle(new Point((int) m.x, (int) m.y),
					new Point((int) (width / 2 - m.size), (int) (height * 1.5)));

			m.x += m.speed * Math.cos(angle);
			m.vx = m.speed * Math.cos(angle);
			m.y += m.speed * Math.sin(angle);
			m.vy = m.speed * Math.sin(angle);

			m.paint(p);
		}

		p.setColor(Color.BLACK);
		FontMetrics metrics = p.getFontMetrics(Painter.font2);
		int xx = (int) ((250 - metrics.stringWidth(pressedDisp)) / 2);
		int yy = (int) (((100 - metrics.getHeight()) / 2) + metrics.getAscent());
		p.setFont(font2);
		p.setColor(Color.WHITE);
		p.drawString(NumberFormat.getNumberInstance(Locale.US).format(pontos), 10, font2.getSize());
		p.setColor(Color.GREEN);
		p.drawString("LVL " + NumberFormat.getNumberInstance(Locale.US).format(level + 1), 10, font2.getSize() * 2);
		p.setColor(new Color(0, 255, 0, levelUp * 5));

		p.drawString("+1", 150, font2.getSize() * 2 + levelUp / 3 - 50 / 3);

		int color = 0;
		if (pontosUp / (5 * multiplier) > 255) {
			color = 255;
		} else {
			color = pontosUp / (5 * multiplier);
		}
		try {
			p.setColor(new Color(0, 255, 0, color));
		} catch (Exception e) {
			e.printStackTrace();
		}
		p.drawString("+" + NumberFormat.getNumberInstance(Locale.US).format(addPontos), 230,
				font2.getSize() + pontosUp / (multiplier * 30) - addPontos / (multiplier * 30));
		p.setColor(Color.WHITE);
		if (levelUp > 0) {
			levelUp--;
		}
		if (addic > 0) {
			pontos += addic / param;
			addic /= param;
			System.out.println(addic);
		}
		if (pontosUp > 0) {
			pontosUp -= 10 * multiplier;
		}
		n = 100 - ((int) (health) > 0 ? (int) (health) / 100 : 0);

		int re = (255 * n) / 100;
		int gr = (255 * (100 - n)) / 100;
		int bl = 0;

		if (index > (choices % 2 == 0 ? choices / 2 - 1 : choices / 2)) {
			index = (choices % 2 == 0 ? -choices / 2 + 1 : -choices / 2);
		} else if (index < (choices % 2 == 0 ? -choices / 2 + 1 : -choices / 2)) {
			index = (choices % 2 == 0 ? choices / 2 - 1 : choices / 2);
		}
		p.setColor(new Color(re, gr, bl));
		p.fillRect(0, height - 10, width - n * (width / 100 + 1), 15);
		p.drawRect(width / 2 + index * (width / choices) - 50, height / 4 * 3 - 50, 100, 100);
		try {
			String correct = Integer.toString(meteors.get(0).n1 * meteors.get(0).n2);
			Random random = new Random();

			if (actualize) {
				ind = new String[choices];
				actualize = false;
				for (int i = 0; i < choices; i++) {
					while (true) {
						ind[i] = Integer.toString(
								meteors.get(0).n1 * meteors.get(0).n2 + random.nextInt(level + random.nextInt(2) + 1)
										- random.nextInt(meteors.get(0).n1 * meteors.get(0).n2));
						if (!ind[i].equals(Integer.toString(meteors.get(0).n1 * meteors.get(0).n2))) {
							break;
						}
					}
				}
				ind[r.nextInt(choices)] = correct;
			}
		} catch (Exception e) {
		}
		p.setFont(font2);
		p.setColor(Color.WHITE);
		try {
			for (int i = 0; i < choices; i++) {
				p.drawString(ind[i], width / 2 + (i - choices / 2) * (width / choices) - 50, height / 4 * 3);
			}
		} catch (Exception e) {
		}

		if (n == 100) {
			Thread thread = new Thread(this);
			thread.start();
			errou = 10;
			pressedDisp = "";
			pressed = "";
		}

		p.setColor(new Color(255, 255, 255, (int) (countDown2 * 25.5)));
		if (upCountDown) {
			countDown2++;
			if (countDown2 >= 10) {
				countDown2 = 10;
				upCountDown = false;
			}
		}

		if (countDown == 0) {
			countDown2--;
			if (countDown2 <= 0) {
				countDown2 = 0;
			}
		} else {
			countDown--;
		}
		if (acertou != 0) {
			acertou /= 1.05;
		}
		if (errou != 0) {
			errou /= 1.05;
		}

		p.drawString(pressedDisp, (int) (width / 2 + xx - 110), (int) (height - 200 + yy));
		p.setColor(new Color(0, 255, 0, acertou * 20));
		p.fillRect(0, 0, width, height);
		p.setColor(new Color(255, 0, 0, errou * 20));
		p.fillRect(0, 0, width, height);

		frames++;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();
	}

	private void paintBackground(Graphics2D p) {
		p.drawImage(BACKGROUND, 0, 0, null);
	}

	private void paintLandscape(Graphics2D p) {
		p.drawImage(LANDSCAPE, 0, height / 2, null);
	}

	public float getAngle(Point target, Point target2) {
		float angle = (float) Math.toDegrees(Math.atan2(target.y - target2.y, target.x - target2.x));

		if (angle < 0) {
			angle += 360;
		}

		return angle;
	}

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		return bimage;
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return dimg;
	}

	public static Image loadImage(String url) {
		return new ImageIcon(new Rank().getClass().getResource(url)).getImage();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		countDown = 100;
		upCountDown = true;

		if (pressed.length() > 3 || pressedDisp.length() > 3) {
			pressed = "";
			pressedDisp = "";
		}
		if (e.getKeyCode() != 37 && e.getKeyCode() != 39 && e.getKeyCode() != 40) {
			if (e.getKeyChar() == '0' || e.getKeyChar() == '1' || e.getKeyChar() == '6' || e.getKeyChar() == '7'
					|| e.getKeyChar() == '2' || e.getKeyChar() == '4' || e.getKeyChar() == '5' || e.getKeyChar() == '8'
					|| e.getKeyChar() == '3' || e.getKeyChar() == '9') {
				pressed += e.getKeyChar();
				pressedDisp += e.getKeyChar();
			}
			try {
				if (Integer.toString(meteors.get(0).n1 * meteors.get(0).n2).charAt(pressed.length() - 1) != e
						.getKeyChar()) {
					pressed = "";
					pressedDisp = "";
					errou = 10;
					Thread t = new Thread(new HealthKiller(500, 2));
					t.start();
					threads.add(t);
				}
			} catch (Exception xe) {
				xe.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int kc = e.getKeyCode();

		if (kc == 37) {
			index--;
		} else if (kc == 39) {
			index++;
		} else if (kc == 40) {
			pressed = ind[index + choices / 2];
			System.out.println(pressed + " " + Integer.toString(meteors.get(0).n1 * meteors.get(0).n2));
			try {
				if (!Integer.toString(meteors.get(0).n1 * meteors.get(0).n2).equals(pressed)) {
					pressed = "";
					pressedDisp = "";
					errou = 10;
					Thread t = new Thread(new HealthKiller(500, 2));
					t.start();
					threads.add(t);
				}
			} catch (Exception xe) {
				try {
					if (Integer.toString(meteors.get(0).n1 * meteors.get(0).n2).length() < pressed.length()) {
						pressed = "";
						pressedDisp = "";
						errou = 10;
						System.out.println("tam " + pressed.length());
						Thread t = new Thread(new HealthKiller(500, 2));
						t.start();
						threads.add(t);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}

		System.out.println("+" + e.getKeyCode());

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void run() {

	}
}
