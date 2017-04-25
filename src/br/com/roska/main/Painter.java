package br.com.roska.main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
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
import br.com.roska.model.Button;
import br.com.roska.model.Meteor;
import br.com.roska.screens.Credits;
import br.com.roska.screens.EasterEgg;
import br.com.roska.screens.GameOver;
import br.com.roska.screens.Leaderboard;
import br.com.roska.screens.Screen;
import br.com.roska.screens.Tutorial01;
import br.com.roska.screens.Tutorial02;
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
	public static final int MAX_IMAGE = 23;
	public static final int MIN_IMAGE = 4;
	public static final int WIDTHSET = 800;
	public static final int PADDINGWIDTH = 2;
	public static final int AJUST = 3;
	public static final int REALOC = 20;
	public static final int FPS = 100;

	public static final int SCREEN_GAME = 0;
	public static final int SCREEN_MENU = 1;
	public static final int SCREEN_CREDITS = 2;
	public static final int SCREEN_TUTORIAL = 3;
	public static final int SCREEN_TUTORIAL2 = 4;
	public static final int SCREEN_GAMEOVER = 5;
	public static final int SCREEN_LEADERBOARD = 6;
	public static final int SCREEN_EASTER_EGG = 7;

	public static final int MENU_BUTTON_PLAY = Screen.MENU_BUTTON_PLAY;
	public static final int MENU_BUTTON_CREDITS = Screen.MENU_BUTTON_CREDITS;
	private static final int MENU_BUTTON_MENU = Screen.MENU_BUTTON_MENU;
	private static final int MENU_BUTTON_TUTORIAL = Screen.MENU_BUTTON_TUTORIAL;
	private static final int MENU_BUTTON_TUTORIAL2 = Screen.MENU_BUTTON_TUTORIAL2;
	private static final int MENU_BUTTON_ANTERIOR = Screen.MENU_BUTTON_ANTERIOR;
	private static final int MENU_BUTTON_QUIT = Screen.MENU_BUTTON_QUIT;
	private static final int MENU_BUTTON_REPETIR = Screen.MENU_BUTTON_REPETIR;
	private static final int MENU_BUTTON_LEADERBOARD = Screen.MENU_BUTTON_LEADERBOARD;

	public static final int BUTTON_HEIGTH = Screen.BUTTON_HEIGTH;
	public static final int BUTTON_WIDTH = Screen.BUTTON_WIDTH;
	public static final int BUTTON_MARGIN = Screen.BUTTON_MARGIN;
	public static final int BUTTON_BORDER = Screen.BUTTON_BORDER;

	public static final String BUTTON_PLAY = Screen.BUTTON_PLAY;
	public static final String BUTTON_CREDITS = Screen.BUTTON_CREDITS;

	private static final int BUTTON_CANCELAR = -1;
	private static final int BUTTON_PROCEDER = -2;

	public static AudioInputStream SHOT_0;
	private int NAVE_POS_Y;

	public static Image BACKGROUND;
	public static Image HEART;
	public static Image SHIP;
	public static Image DANO;
	public static Image MIRA;
	public static Image BOSSIMAGE0;
	public static Image BOSSIMAGE1;

	private Image[] SHIPS;
	private Image[] DANOS;

	private Image ORIGINAL_MIRA;

	public static List<Meteor> meteors = new ArrayList<>();
	public static List<Thread> threads = new ArrayList<>();
	public static String[] emocao = { "PARABÉNS!", "MUITO BOM!", "SHOW!", "INSANO!", "ÓTIMO!", "EXTREMO!", "GÊNIO!",
			"ACERTOU!", "FANTÁSTICO!", "INCRÍVEL!", "ÚNICO!" };

	public static String[] ind;

	public static String pressed = "";
	public static String pressedDisp = "";
	public static String elogio;
	public static String correct;
	public static String name;

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
	public static int multiplier = 31;
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
	public static int rainbow;
	public static int overButton1;
	public static int soundIndex;

	public static boolean devMode = false;
	public static boolean BOT = false;
	public static boolean playsounds = true;

	public static boolean add = true;
	public static boolean cando = true;
	public static boolean once;
	public static boolean stopado;
	public static boolean adultMode = false;
	public static boolean upCountDown = false;
	public static boolean actualize = true;

	public static long pontos;

	public static double param = 2;
	public static double health;
	public static double rotation;

	public static Font font;
	public static Font font4;
	public static Font font2;
	public static Font font3;

	public static Screen currentScreen;

	public static Menu menu;
	public static Tutorial01 tutorial01;
	public static Tutorial02 tutorial02;
	public static Credits credits;
	public static GameOver gameover;
	public static Leaderboard leaderboard;
	public static Meteor meteorDestroy;
	public static EasterEgg easterEgg;

	public static LeaderbordManager lm;
	public static Button b = new Button();
	public static Logger logger;
	public static ImageUtil imageUtil;
	public static SoundThread mainThread;
	public static Popup popup;
	public static Popup popupName;

	private static JFrame frame;

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
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(e);
			return false;
		}
		return true;
	}

	public static void playSound(String sound, int rep, int sec) {
		if (playsounds) {
			SoundThread st = new SoundThread(sound, rep, sec);
			Thread t = new Thread();
			t.start();
			if (sound.equals(Driver.MAIN_MUSIC)) {
				mainThread = st;
			}
		}
	}

	public boolean initializeParameters(JFrame frame) {
		try {

			System.gc();

			logger = new Logger();

			width = frame.getContentPane().getWidth() + 10;
			height = frame.getContentPane().getHeight() + 5;

			Painter.frame = frame;

			imageUtil = new ImageUtil();
			lm = new LeaderbordManager();

			menu = new Menu();
			credits = new Credits();
			tutorial01 = new Tutorial01();
			tutorial02 = new Tutorial02();
			gameover = new GameOver();
			leaderboard = new Leaderboard();
			easterEgg = new EasterEgg();

			SHIPS = new Image[MAX_IMAGE];
			DANOS = new Image[MAX_IMAGE];

			NAVE_POS_Y = height / 4 * 3;

			health = HEALTH;
			add = true;
			screen = SCREEN_MENU;
			currentScreen = menu;

			popupName = new Popup("DIGITE UM NOME");
			popupName.textField = true;
			Button button = new Button(0, 0, 200, 40, "CANCELAR", BUTTON_CANCELAR);
			button.activated = true;
			popupName.addButton(button);
			button = new Button(0, 0, 200, 40, "PROCEDER", BUTTON_PROCEDER);
			button.activated = true;
			popupName.addButton(button);

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

			ORIGINAL_MIRA = imgU.transform(imgL.getPath("mira.png"), 150, 150, Color.GREEN);

			BACKGROUND = imgU.resize(imgL.getPath("space3.jpg"), width, height);
			HEART = imgU.resize(imgL.getPath("heart1.png"), 32, 32);
			SHIP = imgU.resize(imgL.getPath("nave4.png"), width / 4, height / 4);

			tutorial01.IMAGE = imgU.transform(imgL.getPath("tutorial00.png"), width / 3 * 2, height / 3 * 2,
					Color.WHITE);
			tutorial02.IMAGE = imgU.resize(imgL.getPath("tutorial02.png"), width / 3 * 2, height / 3 * 2);

			BOSSIMAGE0 = imgU.resize(imgL.getPath("boss0.png"), width / 3, height / 3);
			BOSSIMAGE1 = imgU.resize(imgL.getPath("boss1.png"), width / 3, height / 3);

			for (int i = MIN_IMAGE; i < MAX_IMAGE; i++) {
				SHIPS[i - MIN_IMAGE] = imgU.resize(imgL.getPath("nave" + i + ".png"), width / 4, height / 4);
			}
			for (int i = MIN_IMAGE; i < MAX_IMAGE; i++) {
				imgU.setImage(imgU.resize(imgL.getPath("nave" + i + ".png"), width / 4, height / 4));
				DANOS[i - MIN_IMAGE] = imgU.dye(new Color(255, 0, 0, 50));
			}

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
					if (Ticker.onBossBattle) {
						if (Ticker.boss != null) {
							Ticker.boss.paint(p);
						}
					}
					if (meteors != null && pressed != null)
						for (int i = meteors.size() - 1; i >= 0; i--) {
							if (meteors.size() > i) {
								try {
									Meteor m = meteors.get(i);
									m.paint(p);
									if (i == 0) {
										imageUtil.setImage(ORIGINAL_MIRA);
										MIRA = imageUtil.resize((int) m.size + 20, (int) m.size + 20);
										p.drawImage(MIRA, (int) (m.x - MIRA.getWidth(null) / 2 + m.size),
												(int) (m.y - MIRA.getHeight(null) / 2 + m.size), null);
									}
								} catch (Exception e) {

								}
							}
						}
					p.setColor(Color.BLACK);
					FontMetrics metrics = p.getFontMetrics(Painter.font2);
					int xx = (int) ((250 - metrics.stringWidth(pressedDisp)) / 2);
					int yy = (int) (((100 - metrics.getHeight()) / 2) + metrics.getAscent());
					p.setFont(font2);
					int realocator = REALOC;
					p.setColor(Color.WHITE);
					p.drawString(NumberFormat.getNumberInstance(Locale.US).format(pontos) + " PONTOS", 10,
							font2.getSize() + realocator);
					p.setColor(Color.GREEN);
					p.drawString("NÍVEL " + NumberFormat.getNumberInstance(Locale.US).format(level + 1), 10,
							font2.getSize() * 2 + (int) (realocator * 1.2f));
					if (devMode) {
						p.drawString("ref " + NumberFormat.getNumberInstance(Locale.US).format(refactorNave), 10,
								font2.getSize() * 3 + (int) (realocator * 1.2f));
						p.drawString("nbr " + correct, 10, font2.getSize() * 4 + (int) (realocator * 1.2f));
						p.drawString("rain " + Integer.toString(rainbow), 10,
								font2.getSize() * 5 + (int) (realocator * 1.2f));
						p.drawString("sizeThreads " + Integer.toString(threads.size()), 10,
								font2.getSize() * 9 + (int) (realocator * 1.2f));

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
					p.drawString("+" + NumberFormat.getNumberInstance(Locale.US).format(addPontos), 300, font2.getSize()
							+ pontosUp / (multiplier * 30) - addPontos / (multiplier * 30) + realocator);
					p.setColor(Color.WHITE);

					int tamanho = (int) (HEALTH * HEALTH / 1E5);

					for (int i = 0; i < health / 1000; i++) {
						imageUtil.setImage(HEART);
						p.drawImage(HEART, (width / 2 - tamanho / 2) + i * (int) (tamanho / (HEALTH / 1000))
								- imageUtil.toBufferedImage().getWidth() / 8 + imageUtil.getImage().getWidth(null) / 4,
								height - 50, null);
					}

					p.setFont(font2);
					p.setColor(Color.WHITE);

					int widthSet = WIDTHSET;

					FontMetrics metrics2 = g.getFontMetrics(Painter.font);
					int ajust = AJUST;
					try {
						for (int i = 0; i < choices; i++) {
							if (ind != null) {
								if (ind[i] != null) {
									metrics2 = g.getFontMetrics();
									if (i == index + choices / 2) {
										p.setFont(new Font(p.getFont().getFontName(), p.getFont().getStyle(),
												(int) (p.getFont().getSize() * 1.2)));
									} else {
										p.setFont(font2);
									}
									p.drawString(ind[i],
											(int) (width / 2 - widthSet / 2 - (int) metrics2.stringWidth(ind[i]) / 2
													+ (i + 0.5) * (widthSet / choices)),
											height / 4 * 3);
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(e);
					}
					if (Painter.index > (Painter.choices % 2 == 0 ? Painter.choices / 2 - 1 : Painter.choices / 2)) {
						Painter.index = (Painter.choices % 2 == 0 ? Painter.choices / 2 - 1 : Painter.choices / 2);
					} else if (Painter.index < (Painter.choices % 2 == 0 ? -Painter.choices / 2 + 1
							: -Painter.choices / 2)) {
						Painter.index = (Painter.choices % 2 == 0 ? -Painter.choices / 2 + 1 : -Painter.choices / 2);
					}
					int paddingWidth = PADDINGWIDTH;
					p.setColor(new Color(9, 186, 0));
					try {
						if (devMode && ind != null && ind[index + choices / 2] != null) {
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
					Font mod = new Font(f.getFontName(), f.getStyle(), f.getSize() + fontSizeLol + 20);
					p.setFont(mod);
					metrics2 = p.getFontMetrics(mod);
					if (levelUp > 0) {
						rainbow += 10;
						p.setColor(new Color((int) Math.abs((Math.sin(Math.toRadians(rainbow + 120)) * 255)),
								(int) (Math.abs(Math.sin(Math.toRadians(rainbow + 240)) * 255)),
								(int) Math.abs((Math.sin(Math.toRadians(rainbow + 360)) * 255))));

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

					if (countDown2 <= 10) {
						p.setColor(new Color(255, 255, 255, (int) (countDown2 * 25.5)));
					}
					p.drawString(pressedDisp, (int) (width / 2 + xx - 110), (int) (height - 200 + yy));
					if (acertou > 0) {
						p.setColor(new Color(0, 255, 0, acertou * 10));
						p.fillRect(0, 0, width, height);
					}
					if (errou > 0) {
						p.setColor(new Color(255, 0, 0, errou * 10));
						p.fillRect(0, 0, width, height);
					}
				}
				if (GAMEOVER) {
					if (overMatrix < 254)
						overMatrix += 3;
					p.setColor(new Color(255 - overMatrix, 255 - overMatrix, 255 - overMatrix));
					p.fillRect(0, 0, width, height);
					if (overMatrix == 255) {
						if (overString < 254) {
							overString += 4;
						}
						if (overString > 255) {
							overString = 255;
						}
						p.setColor(new Color(overString, overString, overString));
						p.setFont(font3);
						p.drawString("GAME OVER", width / 2 - 300, height / 2 - font3.getSize() / 2);
						if (overString >= 254) {
							if (overButton1 < 255) {
								overButton1 += 3;
							}
							if (overButton1 > 255) {
								overButton1 = 255;
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
			} else if (screen == SCREEN_CREDITS) {
				credits.paint(p, this);
			} else if (screen == SCREEN_CREDITS) {
				credits.paint(p, this);
			} else if (screen == SCREEN_TUTORIAL) {
				tutorial01.paint(p, this);
			} else if (screen == SCREEN_TUTORIAL2) {
				tutorial02.paint(p, this);
			} else if (screen == SCREEN_GAMEOVER) {
				gameover.paint(p, this);
			} else if (screen == SCREEN_LEADERBOARD) {
				leaderboard.paint(p, this);
			} else if (screen == SCREEN_EASTER_EGG) {
				easterEgg.paint(p, this);
			}

			if (popup != null) {
				popup.paint(p);
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
		int navex = refactorNave - transposeW(7);
		int navey = height - transposeH(200);

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
				p.draw(new Line2D.Float(navex + transposeW(90), navey + transposeW(80), (int) (d.x + d.size),
						(int) (d.y + d.size)));
				p.draw(new Line2D.Float(navex - transposeW(90), navey + transposeW(80), (int) (d.x + d.size),
						(int) (d.y + d.size)));
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

	private int transposeH(int i) {
		return (int) (height * i / 720);
	}

	private int transposeW(int i) {
		return (int) (width * i / 1350f);
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

	public static void paintBackground(Graphics2D p) {
		p.drawImage(BACKGROUND, 0, 0, null);
	}

	public void paintLandscape(Graphics2D p) {
		int reloc = refactorNave;

		int expr = (level) / 2;

		if (expr >= MAX_IMAGE - MIN_IMAGE - 1) {
			expr = MAX_IMAGE - MIN_IMAGE - 1;
		}

		SHIP = SHIPS[expr];
		DANO = DANOS[expr];

		if (SHIP != null) {
			if (errou == 0) {
				try {
					imageUtil.setImage(SHIP);
					int width = imageUtil.toBufferedImage().getWidth();
					p.drawImage(SHIP, -width / 2 + reloc, NAVE_POS_Y, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				imageUtil.setImage(DANO);
				int width = imageUtil.toBufferedImage().getWidth();
				p.drawImage(DANO, -width / 2 + reloc, NAVE_POS_Y, null);
			}
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

				try {
					if (meteors.size() > 0) {
						if (Integer.toString(meteors.get(0).n1 * meteors.get(0).n2).charAt(pressed.length() - 1) != e
								.getKeyChar()) {
							pressed = "";
							pressedDisp = "";
							errou = 10;
							Thread t = new Thread(new HealthKiller(500, 2));
							t.start();
							threads.add(t);
						}
					}
				} catch (Exception xe) {
					xe.printStackTrace();
					logger.log(xe);
				}
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
					if (meteors.size() > 0) {
						if (Integer.toString(meteors.get(0).n1 * meteors.get(0).n2).length() < pressed.length()) {
							pressed = "";
							pressedDisp = "";
							errou = 10;
							Thread t = new Thread(new HealthKiller(500, 2));
							t.start();
							threads.add(t);
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.log(e2);
				}
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == 112) {
			screen++;
			currentScreen = menu;
			if (screen > 1) {
				screen = SCREEN_GAME;
				currentScreen = null;
			}
		}

	}

	public static void buttonPressed() {

		for (Button but : currentScreen.buttons) {
			but.hover = false;
		}
		if (b.activated) {

			if (b.id == MENU_BUTTON_PLAY) {
				// screen = SCREEN_GAME;
				// currentScreen = null;
				popup = popupName;

			}
			if (b.id == MENU_BUTTON_CREDITS) {
				screen = SCREEN_CREDITS;
				currentScreen = credits;
			}
			if (b.id == MENU_BUTTON_MENU) {
				screen = SCREEN_MENU;
				currentScreen = menu;
				if (stopado) {
					resetGame();
					Painter.playSound(Driver.MAIN_MUSIC, SoundThread.REPEAT, Driver.MAIN_MUSIC_LOOP_TIME);
					stopado = false;
					GAMEOVER = false;
				}
			}
			if (b.id == MENU_BUTTON_TUTORIAL || b.id == MENU_BUTTON_ANTERIOR) {
				screen = SCREEN_TUTORIAL;
				currentScreen = tutorial01;
			}
			if (b.id == MENU_BUTTON_TUTORIAL2) {
				screen = SCREEN_TUTORIAL2;
				currentScreen = tutorial02;
			}
			if (b.id == MENU_BUTTON_REPETIR) {
				resetGame();
				GAMEOVER = false;
				currentScreen = null;
				Painter.playSound(Driver.MAIN_MUSIC, SoundThread.REPEAT, Driver.MAIN_MUSIC_LOOP_TIME);
				screen = SCREEN_GAME;
				stopado = false;
			}
			if (b.id == MENU_BUTTON_LEADERBOARD) {
				screen = SCREEN_LEADERBOARD;
				currentScreen = leaderboard;
			}
			if (b.id == MENU_BUTTON_QUIT) {
				System.exit(-1);
			}
			b.activated = false;
		}

		Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		Painter.frame.setCursor(cursor);

	}

	private static void resetGame() {

		health = HEALTH;

		level = 0;
		levelUp = 0;
		acertou = 0;
		errou = 0;
		addPontos = 0;
		pontos = 0;
		pontosUp = 0;
		refactorNave = 0;
		gameover.ticks = 0;
		Ticker.boss = null;
		Ticker.onBossBattle = false;
		Ticker.onceBossBattle = true;

		add = true;

		meteors = new ArrayList<>();

	}

	public static void buttonPressedPopup() {
		for (Button but : popup.buttons) {
			but.hover = false;
		}
		if (b.activated) {
			if (b.id == BUTTON_PROCEDER) {
				screen = SCREEN_GAME;
				currentScreen = null;
				name = popup.fieldText;
				if (name.contains("-RSKT")) {
					String[] s = name.split(" ");
					if (s.length > 1) {
						Ticker.BOT_SPEED = Integer.parseInt(s[1]);
					}
					if (s.length > 2) {
						Ticker.STEP = Integer.parseInt(s[2]);
					}
					if (s.length > 3) {
						Ticker.STARTBOSS = Integer.parseInt(s[3]);
					}
					if (s.length > 4) {
						BOT = Boolean.parseBoolean(s[4]);
					}

				}
				popup = null;
			}
			if (b.id == BUTTON_CANCELAR) {
				popup = null;

			}
			b.activated = false;
		}
	}

}
