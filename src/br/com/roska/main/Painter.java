package br.com.roska.main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.roska.images.Reference;
import br.com.roska.model.Meteor;
import br.com.roska.threads.HealthKiller;
import br.com.roska.threads.SoundThread;
import br.com.roska.threads.Ticker;
import br.com.roska.util.ImageLoader;
import br.com.roska.util.ImageUtil;
import br.com.roska.util.Logger;

public class Painter extends JPanel implements KeyListener {

	public static final long serialVersionUID = 4073472496026368671L;

	public static final double HEALTH = 5000;
	public static boolean GAMEOVER;
	public static final int MAX_IMAGE = 17;
	public static final int WIDTHSET = 800;
	public static final int PADDINGWIDTH = 2;
	public static final int AJUST = 3;
	public static final int REALOC = 20;
	public static final int FPS = 60;
	public static final int SCREEN_MENU = 1;
	public static final int SCREEN_GAME = 0;

	public static AudioInputStream SHOT_0;

	public static Image BACKGROUND;
	public static Image HEART;
	public static Image SHIP;
	public static Image DANO;

	public static List<Meteor> meteors = new ArrayList<>();
	public static List<Thread> threads = new ArrayList<>();
	public static String[] emocao = { "PARABÉNS!", "MUITO BOM!", "SHOW!", "INSANO!", "ÓTIMO!", "EXTREMO!", "GÊNIO!",
			"ACERTOU!" };
	public static String[] ind;

	public static String pressed = "";
	public static String pressedDisp = "";
	public static String elogio;
	public static String correct;

	public static int countDown;
	public static int countDown2;
	public static int acertou;
	public static int errou;
	public static int n;
	public static int level;
	public static int lastLevel;
	public static int levelUp;
	public static int pontosUp;
	public static int addPontos;
	public static int multiplier = 19;
	public static int addic;
	public static int width;
	public static int refactorNave;
	public static int height;
	public static int index;
	public static int choices = 5;
	public static int overMatrix;
	public static int overString;
	public static int fontSizeLol;
	public static int typeKill;
	public static int particles;
	public static int screen;
	public static int rainbow = 0;
	public static int overButton1;

	public static boolean adultMode = false;
	public static boolean upCountDown = false;
	public static boolean actualize = true;
	public static boolean devMode = true;
	public static boolean add = true;
	public static boolean cando = true;
	public static boolean once;

	public static long pontos;

	public static double param = 2;
	public static double health;
	public static double rotation;

	public static Font font;
	public static Font font4;
	public static Font font2;
	public static Font font3;

	public static ImageUtil imageUtil;
	public static Menu menu;
	public static Logger logger;
	public static Meteor meteorDestroy;

	public Painter(JFrame frame) {

		if (initializeParameters(frame)) {
			print("PARAMETERS OK");
		} else {
			print("ERROR ON INITIALIZING PARAMETERS");
		}

		if (loadImages()) {
			print("IMAGES OK");
		} else {
			print("ERROR ON LOADING IMAGES");
		}

		if (loadFonts()) {
			print("FONTS OK");
		} else {
			print("ERROR ON LOADING FONTS");
		}

		if (loadSounds()) {
			print("SOUNDS OK");
		} else {
			print("ERROR ON LOADING SOUNDS");
		}

		if (finalizeInitialization()) {
			print("FINALIZED OK");
		} else {
			print("ERROR ON FINALIZING INITIALIZATION");
		}
	}

	private boolean finalizeInitialization() {
		try {
			Thread t = new Thread(new Ticker());
			t.start();
			threads.add(t);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean loadSounds() {
		try {
			/// SoundLoader<Reference> soundL = new SoundLoader<Reference>(new
			/// Reference());
			// SHOT_0 =
			/// AudioSystem.getAudioInputStream(soundL.getPath("shot_1.wav"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(e);
			return false;
		}
		return true;
	}

	public static void playSound(String sound) {
		Thread t = new Thread(new SoundThread());
		t.start();
		threads.add(t);
	}

	public boolean initializeParameters(JFrame frame) {
		try {

			logger = new Logger();

			width = frame.getContentPane().getWidth() + 10;
			height = frame.getContentPane().getHeight() + 5;

			imageUtil = new ImageUtil();

			menu = new Menu();

			health = HEALTH;
			// tutorial = 0;
			add = true;
			screen = SCREEN_MENU;

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(e);
			return false;
		}
		return true;
	}

	public boolean loadFonts() {
		try {

			String fontName = "Minecraftia 2.0";
			URL fontSource = new ImageLoader<Reference>(new Reference()).getPath("Minecraftia-Regular.ttf");
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			InputStream myStream = new BufferedInputStream(fontSource.openStream());
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));

			logger.log("===========================");
			logger.log(ge.toString() + "   " + myStream.toString());
			logger.log("===========================");

			font = new Font(fontName, Font.BOLD, 25);
			font4 = new Font(fontName, Font.BOLD, 20);
			font2 = new Font(fontName, Font.BOLD, 30);
			font3 = new Font(fontName, Font.BOLD, 100);

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(e);
			return false;
		}
		return true;
	}

	public void print(Object obj) {
		try {
			System.out.println(obj.toString());
		} catch (Exception e) {

		}
	}

	public boolean loadImages() {

		try {

			ImageUtil imgU = new ImageUtil();
			ImageLoader<Reference> imgL = new ImageLoader<Reference>(new Reference());

			Meteor.IMAGE = imgU.resize(imgL.getPath("meteoro6.png"), 64, 64);

			imgU.setImage(Meteor.IMAGE);
			Meteor.DESTROCO_0 = imgU.resize(16, 16);

			imgU.setImage(Meteor.IMAGE);
			Meteor.DESTROCO_1 = imgU.resize(64, 64);

			imgU.setImage(Meteor.IMAGE);
			Meteor.PARTICLE = imgU.resize(64, 64);

			BACKGROUND = imgU.resize(imgL.getPath("space3.jpg"), width, height);
			HEART = imgU.resize(imgL.getPath("heart1.png"), 32, 32);
			SHIP = imgU.resize(imgL.getPath("nave4.png"), width / 3, height / 3);

			imgU.setImage(SHIP);

			DANO = imgU.dye(new Color(255, 0, 0, 60));
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(e);
			return false;
		}
		return true;
	}

	public void paint(Graphics g) {

		Graphics2D p = initiateGraphics(g);
		Long msec = System.currentTimeMillis();

		Random r = new Random();

		try {
			width = Driver.frame.getWidth();
		} catch (Exception e) {
		}

		try {

			if (screen == SCREEN_GAME) {
				if (!GAMEOVER) {
					if (meteors != null && pressed != null)
						for (int i = 0; i < meteors.size(); i++) {
							Meteor m = meteors.get(i);
							m.paint(p);
						}
					p.setColor(Color.BLACK);
					FontMetrics metrics = p.getFontMetrics(Painter.font2);
					int xx = (int) ((250 - metrics.stringWidth(pressedDisp)) / 2);
					int yy = (int) (((100 - metrics.getHeight()) / 2) + metrics.getAscent());
					p.setFont(font2);
					int realocator = REALOC;
					p.setColor(Color.WHITE);
					p.drawString(NumberFormat.getNumberInstance(Locale.US).format(pontos), 10,
							font2.getSize() + realocator);
					p.setColor(Color.GREEN);
					p.drawString("LVL " + NumberFormat.getNumberInstance(Locale.US).format(level + 1), 10,
							font2.getSize() * 2 + (int) (realocator * 1.2f));
					if (devMode) {
						p.drawString("ref " + NumberFormat.getNumberInstance(Locale.US).format(refactorNave), 10,
								font2.getSize() * 3 + (int) (realocator * 1.2f));
						p.drawString("nbr " + correct, 10, font2.getSize() * 4 + (int) (realocator * 1.2f));
						p.drawString("rain " + Integer.toString(rainbow), 10,
								font2.getSize() * 5 + (int) (realocator * 1.2f));
					}
					p.setColor(new Color(0, 255, 0, levelUp * 5));

					p.drawString("+1", 150, font2.getSize() * 2 + levelUp / 3 - 50 / 3 + realocator);

					int color = 0;
					if (pontosUp / (5 * multiplier) > 255) {
						color = 255;
					} else {
						color = pontosUp / (5 * multiplier);
					}

					try {
						if (color <= 255 && color >= 0)
							p.setColor(new Color(0, 255, 0, color));
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(e);
					}
					p.drawString("+" + NumberFormat.getNumberInstance(Locale.US).format(addPontos), 230, font2.getSize()
							+ pontosUp / (multiplier * 30) - addPontos / (multiplier * 30) + realocator);
					p.setColor(Color.WHITE);

					int tamanho = (int) (HEALTH * HEALTH / 1E5);

					for (int i = 0; i < health / 1000; i++) {
						imageUtil.setImage(HEART);
						p.drawImage(HEART, (width / 2 - tamanho / 2) + i * (int) (tamanho / (HEALTH / 1000))
								- imageUtil.toBufferedImage().getWidth() / 8, height - 50, null);
					}

					// TODO
					// index = correctIndex - choices / 2;
					// pressed = ind[index + choices / 2];

					p.setFont(font2);
					p.setColor(Color.WHITE);

					int widthSet = WIDTHSET;

					FontMetrics metrics2 = g.getFontMetrics(Painter.font);
					int ajust = AJUST;
					try {
						for (int i = 0; i < choices; i++) {
							p.drawString(ind[i],
									(int) (width / 2 - widthSet / 2
											- (int) metrics2.getStringBounds(ind[i], g).getWidth() - ajust
											+ (i + 0.5) * (widthSet / choices)),
									height / 4 * 3);
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(e);
					}

					int paddingWidth = PADDINGWIDTH;
					p.setColor(new Color(9, 186, 0));
					try {
						if (devMode) {
							p.drawRect(
									(int) (width / 2 - widthSet / 2 - paddingWidth
											- (int) metrics2.getStringBounds(ind[index + choices / 2], g).getWidth()
											- ajust + (index + 0.5 + choices / 2) * (widthSet / choices)),
									height / 4 * 3 - 50,
									(int) metrics2.getStringBounds(ind[index + choices / 2], g).getWidth()
											+ paddingWidth * 2,
									metrics2.getHeight());
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(e);
					}
					Font f = p.getFont();
					Font mod = new Font(f.getFontName(), f.getStyle(), f.getSize() + fontSizeLol);
					p.setFont(mod);
					metrics2 = p.getFontMetrics(mod);
					if (levelUp > 0) {
						rainbow += 10;
						p.setColor(new Color((int) Math.abs((Math.sin(Math.toRadians(rainbow)) * 255)),
								(int) (Math.abs(Math.sin(Math.toRadians(rainbow - 120)) * 255)),
								(int) Math.abs((Math.sin(Math.toRadians(rainbow - 240)) * 255))));

						AffineTransform transform = p.getTransform();
						p.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, levelUp / 50f));
						p.rotate(Math.toRadians(rotation), width / 2, height / 2);
						p.drawString(elogio,
								width / 2 - (int) metrics2.getStringBounds(elogio, p).getWidth() / 2 + r.nextInt(3)
										- r.nextInt(3),
								height / 2 - r.nextInt(3) + r.nextInt(3)
										+ (int) metrics2.getStringBounds(elogio, p).getHeight() / 2);
						p.setTransform(transform);
						p.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
					} else {
						rotation = 0;
					}

					p.setColor(new Color(255, 255, 255));

					tryDestroy(p);

					p.setColor(new Color(255, 255, 255, (int) (countDown2 * 25.5)));

					p.drawString(pressedDisp, (int) (width / 2 + xx - 110), (int) (height - 200 + yy));
					p.setColor(new Color(0, 255, 0, acertou * 20));
					p.fillRect(0, 0, width, height);
					p.setColor(new Color(255, 0, 0, errou * 20));
					p.fillRect(0, 0, width, height);

				}
				if (GAMEOVER) {
					if (overMatrix < 255)
						overMatrix++;
					p.setColor(new Color(255 - overMatrix, 255 - overMatrix, 255 - overMatrix));
					p.fillRect(0, 0, width, height);
					if (overMatrix == 255) {
						if (overString < 255) {
							overString += 3;
						}
						p.setColor(new Color(overString, overString, overString));
						p.setFont(font3);
						p.drawString("GAME OVER", width / 2 - 300, height / 2 - font3.getSize() / 2);
						if (overString == 255) {
							if (overButton1 < 255) {
								overButton1 += 3;
							}
							p.setColor(new Color(overButton1, overButton1, overButton1));
							p.fillRect(width / 2 - 100, height / 2 - 10, 200, 50);
							p.setFont(font4);
							p.setColor(Color.BLACK);
							p.drawString("MENU PRINCIPAL", width / 2 - 90, height / 2 + 32 - font4.getSize() / 2);
						}
					}
				}
				if (devMode) {
					p.setFont(font2);
					p.setColor(Color.GREEN);

					p.drawString(
							"slow " + NumberFormat.getNumberInstance(Locale.US)
									.format((1000 / FPS) - (System.currentTimeMillis() - msec)),
							10, font2.getSize() * 7 + (int) (REALOC * 1.2f));
					p.drawString("particles " + NumberFormat.getNumberInstance(Locale.US).format(particles), 10,
							font2.getSize() * 8 + (int) (REALOC * 1.2f));
					p.drawString(
							"msec " + NumberFormat.getNumberInstance(Locale.US)
									.format(System.currentTimeMillis() - msec),
							10, font2.getSize() * 6 + (int) (REALOC * 1.2f));
				}
			} else if (screen == SCREEN_MENU) {
				menu.paint(p, this);
			}

			try {
				Thread.sleep((1000 / FPS) - (System.currentTimeMillis() - msec) > 0
						? (1000 / FPS) - (System.currentTimeMillis() - msec) : 0);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.log(e);
			}
		} catch (Exception k) {
			k.printStackTrace();
			logger.log(k);
		}
		repaint();
	}

	private void tryDestroy(Graphics2D p) {
		int navex = refactorNave - 7;
		int navey = height - 200;

		if (acertou > 0 && meteorDestroy != null) {
			Meteor d = meteorDestroy;
			if (typeKill == 0) {// TODO
				p.setColor(new Color(255, 20, 147));
				p.setStroke(new BasicStroke(acertou * 2));
				p.draw(new Line2D.Float(navex, navey, (int) (d.x + d.size), (int) (d.y + d.size)));
				p.setColor(Color.WHITE);
				p.setStroke(new BasicStroke(acertou * 1));
				p.draw(new Line2D.Float(navex, navey, (int) (d.x + d.size), (int) (d.y + d.size)));
			} else if (typeKill == 1) {
				p.setColor(new Color(0, 0, 255));
				p.setStroke(new BasicStroke(acertou * 2));
				p.draw(new Line2D.Float(navex, navey, (int) (d.x + d.size), (int) (d.y + d.size)));
				p.draw(new Line2D.Float(navex + 90, navey + 80, (int) (d.x + d.size), (int) (d.y + d.size)));
				p.draw(new Line2D.Float(navex - 90, navey + 80, (int) (d.x + d.size), (int) (d.y + d.size)));
				p.setColor(Color.WHITE);
				p.setStroke(new BasicStroke(acertou * 1));
				p.draw(new Line2D.Float(navex, navey, (int) (d.x + d.size), (int) (d.y + d.size)));
			} else if (typeKill == 2) {
				p.setColor(new Color(0, 255, 30));
				p.setStroke(new BasicStroke(acertou * 5));
				p.draw(new Line2D.Float(navex, navey, (int) (d.x + d.size), (int) (d.y + d.size)));
				p.draw(new Line2D.Float(navex + acertou * 2, navey, (int) (d.x + d.size), (int) (d.y + d.size)));
				p.draw(new Line2D.Float(navex - acertou * 2, navey, (int) (d.x + d.size), (int) (d.y + d.size)));
			}
		}
	}

	public Graphics2D initiateGraphics(Graphics p) {

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		Graphics2D e = (Graphics2D) p;

		e.setRenderingHints(rh);
		e.setColor(Color.WHITE);
		paintBackground(e);
		paintLandscape(e);
		e.setColor(Color.BLACK);
		e.setFont(font);

		return e;
	}

	public void paintBackground(Graphics2D p) {
		p.drawImage(BACKGROUND, 0, 0, null);
	}

	public void paintLandscape(Graphics2D p) {
		int reloc = refactorNave;

		if (lastLevel != level) {
			ImageLoader<Reference> imgL = new ImageLoader<Reference>(new Reference());
			int expr = (level) / 2 + 4;
			SHIP = imageUtil.resize(imgL.getPath("nave" + (expr < MAX_IMAGE ? expr : MAX_IMAGE) + ".png"), width / 3,
					height / 3);
			DANO = imageUtil.transform(imgL.getPath("nave" + (expr < MAX_IMAGE ? expr : MAX_IMAGE) + ".png"), width / 3,
					height / 3, new Color(255, 0, 0, 50));
		}

		lastLevel = level;

		if (errou == 0) {
			imageUtil.setImage(SHIP);
			int heigth = imageUtil.toBufferedImage().getHeight();
			p.drawImage(SHIP, -heigth + reloc, height - 200, null);
		} else {
			imageUtil.setImage(DANO);
			int heigth = imageUtil.toBufferedImage().getHeight();
			p.drawImage(DANO, -heigth + reloc, height - 200, null);
		}
	}

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
				logger.log(xe);
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
			try {
				pressed = ind[index + choices / 2];
				// print(pressed + " " + Integer.toString(meteors.get(0).n1 *
				// meteors.get(0).n2));
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
						// print("tam " + pressed.length());
						Thread t = new Thread(new HealthKiller(500, 2));
						t.start();
						threads.add(t);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.log(e2);
				}
			}
		}

		// print("+" + e.getKeyCode());

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// print(e.getKeyCode());

		if (e.getKeyCode() == 112) {
			screen++;
			if (screen > 1) {
				screen = SCREEN_GAME;
			}
		}

	}

}
