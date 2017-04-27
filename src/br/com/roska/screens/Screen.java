package br.com.roska.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import br.com.roska.main.Painter;
import br.com.roska.model.Button;
import br.com.roska.model.Meteor;

public abstract class Screen {

	public List<Button> buttons = new ArrayList<>();
	public List<Meteor> meteors = new ArrayList<>();
	public int width;
	public int height;
	public int x;
	public int y;

	public static final int BUTTON_HEIGTH = 50;
	public static final int BUTTON_WIDTH = 300;
	public static final int BUTTON_MARGIN = 4;
	public static final int BUTTON_BORDER = 5;

	public static final int MENU_BUTTON_PLAY = 0;
	public static final int MENU_BUTTON_CREDITS = 1;
	public static final int MENU_BUTTON_MENU = 2;
	public static final int MENU_BUTTON_TUTORIAL = 3;
	public static final int MENU_BUTTON_QUIT = 4;
	public static final int MENU_BUTTON_TUTORIAL2 = 5;
	public static final int MENU_BUTTON_ANTERIOR = 6;
	public static final int MENU_BUTTON_REPETIR = 7;
	public static final int MENU_BUTTON_LEADERBOARD = 8;

	public static final String BUTTON_PLAY = "JOGAR";
	public static final String BUTTON_CREDITS = "CRÉDITOS";
	public static final String BUTTON_MENU = "MENU PRINCIPAL";
	public static final String BUTTON_TUTORIAL = "TUTORIAL";
	public static final String BUTTON_TUTORIAL2 = "PRÓXIMO >";
	public static final String BUTTON_ANTERIOR = "< ANTERIOR";
	public static final String BUTTON_QUIT = "SAIR";
	public static final String BUTTON_REPETIR = "TENTAR DE NOVO";
	public static final String BUTTON_LEADERBOARD = "RANKING";

	public static final String VERSION = "V2.1";

	public Screen() {
		width = Painter.width;
		height = Painter.height;
	}

	public Screen(Rectangle r) {
		x = (int) r.getX();
		y = (int) r.getY();

		width = (int) r.getWidth();
		height = (int) r.getHeight();
	}

	public abstract void paint(Graphics2D p, Painter painter);

	public void paint(Graphics2D p) {

		Painter.paintBackground(p);
		p.setColor(Color.WHITE);
		p.drawString("Tabuada Espacial " + VERSION, 10, p.getFont().getSize() + 20);

		if (buttons.size() > 0) {
			int index = 0;
			int bQ = buttons.size();

			for (int i = 0; i < meteors.size(); i++) {
				meteors.get(i).paint(p);
			}

			int height0 = 0, width0 = 0;

			for (Button b : buttons) {
				if (b.orientation == Button.TOP || b.orientation == Button.BOTTOM) {
					height0 += b.height + b.margin * 2;
				} else {
					width0 += b.width + b.margin * 2;
				}
			}

			width0 /= bQ;
			height0 /= bQ;

			if (width0 == 0) {
				width0 = BUTTON_WIDTH + BUTTON_BORDER * 2;
			}

			for (Button b : buttons) {
				b.y = height / 2 + (height0 * bQ) / 2 - (height0 * index) + y;
				if (b.orientation == Button.RIGHT || b.orientation == Button.LEFT) {
					b.x = width / 2 - (width0 * bQ) / 2 + (width0 * index) + x;
				} else {
					b.x = width / 2 - width0 / 2;
				}
				b.paint(p);
				index++;
			}
		}
		try {
			Thread.sleep(1000 / Painter.FPS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Painter.logger.log(e);
		}
	}
}
