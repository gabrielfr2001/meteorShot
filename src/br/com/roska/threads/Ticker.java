package br.com.roska.threads;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.roska.main.Painter;
import br.com.roska.model.Boss;
import br.com.roska.model.Meteor;
import br.com.roska.model.Particle;
import br.com.roska.util.Maths;

public class Ticker implements Runnable {

	private static final double METEOR_OCURR = 1;
	private static final int MIN = 1;
	private static final int MAX = 2;
	public static int correctIndex;
	public static int BOT_SPEED = 50;// 50
	public static long STEP = 12;// 12
	public static int STARTBOSS = 20;// 20
	public static int frames = 0;
	public static boolean onBossBattle;
	public static boolean onceBossBattle = true;
	public static boolean atualize;
	public static List<Thread> threadsToRemove = new ArrayList<>();
	public static Boss boss;
	private static int lastResult;
	public static List<Threader> removeThreaders = new ArrayList<>();
	public static boolean startDepreciationBossBattle;
	public static int depreciationStart;

	@Override
	public synchronized void run() {

		Random r = new Random();

		for (;;) {
			if (!Painter.GAMEOVER && Painter.popup != Painter.popupExit) {

				for (int i = 0; i < threadsToRemove.size(); i++) {
					Painter.threads.remove(threadsToRemove.get(i));
					threadsToRemove.remove(threadsToRemove.get(i));
				}
				for (int i = 0; i < removeThreaders.size(); i++) {
					Painter.threads.remove(removeThreaders.get(i));
					removeThreaders.remove(removeThreaders.get(i));
				}

				if (Painter.screen == Painter.SCREEN_GAME) {
					if (Painter.meteors.size() < 10 && Painter.add && !onBossBattle) {
						String statement = "";
						int n1 = 0;
						int n2 = 0;

						if (Painter.adultMode) {
							n1 = r.nextInt(Painter.level + 1) + 1;
							n2 = r.nextInt(Painter.level + 1) + 1;
						} else {
							int value = r.nextInt(10) + 1;
							n1 = value > 10 ? 10 : value;
							value = r.nextInt(10) + 1;
							n2 = value > 10 ? 10 : value;
						}

						statement += n1;
						statement += " x ";
						statement += n2;

						int size = (int) 150;
						Meteor meteor = null;

						for (;;) {
							try {
								int w = Painter.width > 0 ? Painter.width : 1000;

								meteor = new Meteor(new Random().nextInt(w - size * 2 + 1), -size * 1.5, statement,
										size);
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

						meteor.speed = (Math.random() * 0.2 + 0.8);
						meteor.appendRotation = Math.random() - Math.random();
						Painter.add = false;

						List<Meteor> meteors = new ArrayList<Meteor>();
						meteors.add(meteor);

						for (int i = 0; i < Painter.meteors.size(); i++) {
							Meteor m = Painter.meteors.get(i);
							meteors.add(m);
						}

						Painter.meteors = meteors;
						Painter.actualize = true;

					}

					if (onBossBattle) {
						if (boss != null) {
							boss.tick();
						}
						if (startDepreciationBossBattle) {
							depreciationStart--;
							if (depreciationStart <= 0) {
								onBossBattle = false;
								startDepreciationBossBattle = false;
								for (int i = 0; i < Painter.meteors.size(); i++) {
									if (Painter.meteors.get(i).size == 100) {
										Painter.meteors.remove(Painter.meteors.get(i));
									}
								}
								boss.canAdd = true;
							}
						}
					} else {
						boss = null;
					}

					try {
						refactoryMeteor();
					} catch (Exception e) {
						e.printStackTrace();
						Painter.logger.log(e);
					}
					if (Painter.meteors != null && Painter.pressed != null)
						if (!Painter.pressed.equals("") && Painter.meteors.size() > 0)
							if (Painter.cando)
								if (Painter.meteors.get(0).n1 * Painter.meteors.get(0).n2 == Integer
										.parseInt(Painter.pressed)) {
									Threader mainThread = new Threader(Painter.meteors.get(0), Threader.KILL);
									Painter.meteors.get(0).inactive = true;
									mainThread.TYPE = r.nextInt(3);

									Painter.playSound("shot_" + (new Random().nextInt(MAX - MIN + 1) + MIN) + ".wav",
											SoundThread.NOREPEAT, -1);

									Painter.typeKill = mainThread.TYPE;
									Thread thread = new Thread(mainThread);
									Painter.threads.add(thread);
									thread.start();
									Painter.acertou = 10;
									Painter.pressedDisp = "";
									Painter.pressed = "";
									Painter.addPontos = (int) (Painter.height - 100 - Painter.meteors.get(0).y)
											* Painter.multiplier;

									int ponto = (int) (Painter.addPontos * Painter.param);
									int var = 0;

									while (ponto != 0) {
										ponto /= Painter.param;
										var += ponto;
									}

									Painter.addic += Painter.addPontos * Painter.param - var / 2;
									// pontos += addPontos;
									Painter.pontosUp += Painter.addPontos;
									Painter.cando = false;
									Painter.level++;
									Painter.levelUp = 50;
									if (Painter.level % 2 == 1)
										Painter.playSound("levelUp.wav", SoundThread.NOREPEAT, 0);

								}
					for (int i = 0; i < Painter.meteors.size(); i++) {
						if (i < Painter.meteors.size()) {
							Meteor m = null;
							try {
								m = Painter.meteors.get(i);
								for (int a = 0; a < m.particles.size(); a++) {
									try {
										Particle o = m.particles.get(a);
										if (o != null) {
											o.tick();
										}
									} catch (Exception e) {
									}
								}
							} catch (Exception e) {
								Painter.logger.log(e);
							}
							try {
								if (m.particles.size() < 200 && m.canadParticle) {
									for (int e = 0; e < 3; e++) {
										Particle p = new Particle();
										p.x = m.x + m.size - 5 + Math.random() * m.size / 3
												- Math.random() * m.size / 3;
										p.y = m.y + m.size + Math.random() * m.size / 3 - Math.random() * m.size / 2;
										p.active = true;
										p.spreadness = 2;
										p.vy = -m.vy;
										p.vx = -m.vx;
										p.size = 30;
										p.sizeDecay = 0.5;
										p.c = m.particleColor;
										p.life = 70;
										p.rotateness = 10;
										m.particles.add(p);
										Painter.particles++;
									}
								}
							} catch (Exception e) {
								Painter.logger.log(e);
							}
							try {
								if (m != null) {
									if (m.y > Painter.height - m.size - 240 && !m.inactive) {
										Thread thread = new Thread(new Threader(m, Threader.EXPLODE));
										Painter.threads.add(thread);
										thread.start();
										Painter.errou = 10;
										Painter.pressedDisp = "";
										Painter.pressed = "";
										Thread t = new Thread(new HealthKiller(1000, 20));
										t.start();
										Painter.threads.add(t);
									}

									double angle = Maths.getAngle(new Point((int) m.x, (int) m.y), new Point(
											(int) (Painter.width / 2 - m.size), (int) (Painter.height * 1.5)));
									double decaimentGrade = 1;
									if (m.notMoving) {
										decaimentGrade = 0.1;
									}

									double level = Painter.level / (40f + Math.sqrt(Painter.level));

									m.vx = Math.cos(angle) * (level + 0.5) * m.speed;
									m.x += m.vx * decaimentGrade;
									m.vy = Math.sin(angle) * (level + 0.5) * m.speed;
									m.y += m.vy * decaimentGrade;
								}
							} catch (Exception e) {
								Painter.logger.log(e);
							}
						}
					}
					if (Painter.BOT && frames % BOT_SPEED == 0) {
						Painter.index = correctIndex - Painter.choices / 2;
						Painter.pressed = Painter.ind[Painter.index + Painter.choices / 2];
					}

					if (frames % 2 == 0) {
						if (atualize) {
							atualize = false;
							refactoryMeteor();
						}
					}
					others(r);
				} else if (Painter.screen == Painter.SCREEN_MENU) {

					if (r.nextInt(100 * 100) < METEOR_OCURR * 100) {
						Meteor meteor = new Meteor(r.nextInt(Painter.width) - r.nextInt(25) - 125,
								r.nextInt(Painter.height) - r.nextInt(25) - 125, "", r.nextInt(25) + 125);
						Painter.imageUtil.setImage(Meteor.IMAGE);
						meteor.image = Painter.imageUtil.resize((int) meteor.size, (int) meteor.size);
						meteor.appendRotation = Math.random() - Math.random();
						meteor.speed = 1 + Math.random() * 5;

						meteor.targetX = r.nextInt(Painter.width) * 100 * (r.nextBoolean() ? 1 : -1);
						meteor.targetY = r.nextInt(Painter.height) * 100 * (r.nextBoolean() ? 1 : -1);
						meteor.life = 0;

						Painter.menu.meteors.add(meteor);
					}

					for (int i = 0; i < Painter.menu.meteors.size(); i++) {
						Meteor m = Painter.menu.meteors.get(i);
						double angle = Maths.getAngle(new Point((int) m.x, (int) m.y), new Point(m.targetX, m.targetY));

						m.vx = 1 * Math.cos(angle) * m.speed;
						m.x += m.vx;
						m.vy = 1 * Math.sin(angle) * m.speed;
						m.y += m.vy;
						m.life++;

						if (m.particles.size() < 200 && m.canadParticle) {
							for (int e = 0; e < 3; e++) {
								Particle p = new Particle();
								p.x = m.x + m.size - 5 + Math.random() * m.size / 3 - Math.random() * m.size / 3;
								p.y = m.y + m.size + Math.random() * m.size / 3 - Math.random() * m.size / 2;
								p.active = true;
								p.spreadness = 2;
								p.vy = -m.vy;
								p.vx = -m.vx;
								p.size = 30;
								p.sizeDecay = 0.5;
								p.c = m.particleColor;
								p.life = 70;
								p.rotateness = 10;
								m.particles.add(p);
								Painter.particles++;
							}
						}
						for (int a = 0; a < m.particles.size(); a++) {
							try {
								if (m.particles.size() > 0) {
									if (m.particles.size() > 0) {
										Particle o = m.particles.get(a);
										if (o != null) {
											o.tick();
										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								Painter.logger.log(e);
							}
						}

						if (m.life > 500) {
							Painter.menu.meteors.remove(m);
						}

					}
				}
			}
			if (Painter.screen == Painter.SCREEN_GAMEOVER) {
				Painter.gameover.ticks++;
			}

			frames++;
			try {
				Thread.sleep(STEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Painter.logger.log(e);
			}
		}
	}

	private void others(Random r) {
		if (Painter.levelUp > 0) {
			Painter.levelUp--;
		}
		if (Painter.addic > 0) {
			Painter.pontos += Painter.addic / Painter.param;
			Painter.addic /= Painter.param;
		}
		if (Painter.pontosUp > 0) {
			Painter.pontosUp -= 10 * Painter.multiplier;
		}
		if (Painter.health <= 0) {
			doGameOver();
		}
		Painter.n = 100 - ((int) (Painter.health) > 0 ? (int) (Painter.health) / 100 : 0);

		if (Painter.index > (Painter.choices % 2 == 0 ? Painter.choices / 2 - 1 : Painter.choices / 2)) {
			Painter.index = (Painter.choices % 2 == 0 ? Painter.choices / 2 - 1 : Painter.choices / 2);
		} else if (Painter.index < (Painter.choices % 2 == 0 ? -Painter.choices / 2 + 1 : -Painter.choices / 2)) {
			Painter.index = (Painter.choices % 2 == 0 ? -Painter.choices / 2 + 1 : -Painter.choices / 2);
		}
		int preLocator = (int) (Painter.width / 2 - Painter.WIDTHSET / 2 - Painter.PADDINGWIDTH - Painter.AJUST
				+ (Painter.index + 0.5 + Painter.choices / 2) * (Painter.WIDTHSET / Painter.choices));

		int vel = 10;
		if (Painter.refactorNave != preLocator) {
			if (Painter.refactorNave > preLocator) {
				preLocator -= 9;
			} else {
				preLocator += 9;
			}
			Painter.refactorNave += (-Painter.refactorNave + preLocator) / vel > vel * 2
					? (-Painter.refactorNave + preLocator) / vel - vel : (-Painter.refactorNave + preLocator) / vel;
		}

		if (Painter.n == 100) {
			Painter.errou = 10;
			Painter.pressedDisp = "";
			Painter.pressed = "";
		}

		if (Painter.levelUp > 0 && !Painter.once) {
			Painter.elogio = Painter.emocao[r.nextInt(Painter.emocao.length)];
			Painter.once = true;
		} else if (Painter.levelUp == 0) {
			Painter.once = false;
		}
		Painter.fontSizeLol++;
		if (Painter.fontSizeLol > 2) {
			Painter.fontSizeLol = 0;
		}
		if (Painter.upCountDown) {
			Painter.countDown2++;
			if (Painter.countDown2 >= 10) {
				Painter.countDown2 = 10;
				Painter.upCountDown = false;
			}
		}

		if (Painter.level == STARTBOSS) {
			onBossBattle = true;
		}
		if (onBossBattle) {
			if (onceBossBattle) {
				boss = new Boss();
				boss.x = Painter.width / 2;
				boss.IMAGE = Painter.BOSSIMAGE1;
				boss.y = -boss.IMAGE.getHeight(null) / 2;
				onceBossBattle = false;
			}
		}

		if (Painter.countDown == 0) {
			Painter.countDown2--;
			if (Painter.countDown2 <= 0) {
				Painter.countDown2 = 0;
			}
		} else {
			Painter.countDown--;
		}
		if (Painter.acertou != 0) {
			Painter.acertou /= 1.05;
		}
		if (Painter.errou != 0) {
			Painter.errou /= 1.05;
		}
	}

	public static void doGameOver() {
		Painter.GAMEOVER = true;

		Painter.currentScreen = Painter.gameover;
		Painter.screen = Painter.SCREEN_GAMEOVER;
		Painter.threads.set(Painter.soundIndex, null);

		if (Painter.mainThread != null) {
			Painter.mainThread.cancel();
		}

		Painter.playSound("game_over.wav", SoundThread.NOREPEAT, 0);
		Painter.stopado = true;

		Painter.lm.register(Painter.name, Painter.pontos);
	}

	public static void refactoryMeteor() {

		if (Painter.meteors.size() > 0) {
			String correct = Integer.toString(Painter.meteors.get(0).n1 * Painter.meteors.get(0).n2);
			Painter.correct = correct;
		}

		if (lastResult != Integer.parseInt(Painter.correct)) {

			Random random = new Random();

			Painter.ind = new String[Painter.choices];
			boolean repeated = false;
			for (int i = 0; i < Painter.choices; i++) {
				while (true) {
					repeated = false;
					Painter.ind[i] = Integer.toString(Painter.meteors.get(0).n1 * Painter.meteors.get(0).n2
							+ random.nextInt(Painter.level > 20 ? 20 : Painter.level + random.nextInt(5) + 2)
							- random.nextInt((Painter.meteors.get(0).n1 * Painter.meteors.get(0).n2)));
					for (int o = 0; o < Painter.ind.length; o++) {
						if (o != i) {
							if (Painter.ind[i].equals(Painter.ind[o])) {
								repeated = true;
							}
						}
					}
					if (!Painter.ind[i].equals(Integer.toString(Painter.meteors.get(0).n1 * Painter.meteors.get(0).n2))
							&& !repeated) {
						break;
					}
				}
			}
			int randomic = random.nextInt(Painter.choices);
			correctIndex = randomic;
			Painter.ind[randomic] = Painter.correct;
		}
		lastResult = Integer.parseInt(Painter.correct);
	}

}
