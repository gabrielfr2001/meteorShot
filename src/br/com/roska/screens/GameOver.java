package br.com.roska.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.text.NumberFormat;
import java.util.Locale;

import br.com.roska.main.Painter;
import br.com.roska.model.Button;

public class GameOver extends Screen {
	public int ticks;
	public String[] colocations;

	public GameOver() {
		Button menuButtonRepetir = new Button(Painter.width / 2 - BUTTON_WIDTH / 2, 0, BUTTON_WIDTH, BUTTON_HEIGTH,
				BUTTON_REPETIR, MENU_BUTTON_REPETIR);
		menuButtonRepetir.margin = BUTTON_BORDER;
		menuButtonRepetir.orientation = Button.RIGHT;
		buttons.add(menuButtonRepetir);
		Button menuButtonMenu = new Button(Painter.width / 2 - BUTTON_WIDTH / 2, 0, BUTTON_WIDTH, BUTTON_HEIGTH,
				BUTTON_MENU, MENU_BUTTON_MENU);
		menuButtonMenu.margin = BUTTON_BORDER;
		menuButtonMenu.orientation = Button.RIGHT;
		buttons.add(menuButtonMenu);

	}

	@Override
	public void paint(Graphics2D p, Painter painter) {
		super.paint(p);

		Font f = p.getFont();
		p.setColor(Color.GREEN);

		p.setFont(new Font(f.getFontName(), f.getStyle(), 30));
		FontMetrics metrics = p.getFontMetrics();
		String str = "PONTUAÇÃO - " + NumberFormat.getNumberInstance(Locale.US).format(Painter.pontos);
		p.drawString(str, Painter.width / 2 - metrics.stringWidth(str) / 2, Painter.height / 2);

		str = "NÍVEL " + Long.toString(Painter.level + 1);
		p.drawString(str, Painter.width / 2 - metrics.stringWidth(str) / 2,
				Painter.height / 2 - p.getFont().getSize() * 2);

		p.setColor(Color.WHITE);
		p.setFont(new Font(f.getFontName(), f.getStyle(), 60));
		metrics = p.getFontMetrics();
		str = "FIM DE JOGO";
		p.drawString(str, Painter.width / 2 - metrics.stringWidth(str) / 2, Painter.height / 3);

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
					Painter.height / 3 * 2 + (p.getFont().getSize() + 5) * i);
		}

		if (ticks < 255) {
			p.setColor(new Color(0, 0, 0, 255 - ticks));
			p.fillRect(0, 0, Painter.width, Painter.height);
		}
	}

}
