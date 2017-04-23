package br.com.roska.screens;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import br.com.roska.main.Painter;
import br.com.roska.model.Button;

public class Leaderboard extends Screen {

	private static final Rectangle BOUNDS = new Rectangle(0, Painter.height / 2, Painter.width, Painter.height / 2);
	public String[] colocations;

	public Leaderboard() {
		super(BOUNDS);
		Button menuButtonMenu = new Button(Painter.width / 2 - BUTTON_WIDTH / 2, 0, BUTTON_WIDTH, BUTTON_HEIGTH,
				BUTTON_MENU, MENU_BUTTON_MENU);
		menuButtonMenu.margin = BUTTON_BORDER;
		menuButtonMenu.orientation = Button.TOP;
		buttons.add(menuButtonMenu);
	}

	@Override
	public void paint(Graphics2D p, Painter painter) {
		super.paint(p);

		FontMetrics metrics = p.getFontMetrics();
		Font f = p.getFont();

		colocations = Painter.lm.getRanking();

		p.setFont(new Font(f.getFontName(), f.getStyle(), 20));
		metrics = p.getFontMetrics();
		int max = 0;

		for (int i = 0; i < colocations.length; i++) {
			if (max < metrics.stringWidth(colocations[i])) {
				max = metrics.stringWidth(colocations[i]);
			}
		}

		for (int i = 0; i < colocations.length; i++) {
			p.drawString(colocations[i], Painter.width / 2 - max / 2,
					Painter.height / 3 + (p.getFont().getSize() * 2) * i);
		}
	}

}
