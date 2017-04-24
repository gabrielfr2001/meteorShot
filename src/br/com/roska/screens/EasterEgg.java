package br.com.roska.screens;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import br.com.roska.main.Painter;

public class EasterEgg extends Screen {

	private int tick;

	private String message = "Você achou um easter egg: a batata";
	private String message2 = "Bom, não tem como sair daqui de forma prática, então aperte f1";

	@Override
	public void paint(Graphics2D p, Painter painter) {
		super.paint(p);
		tick++;
		FontMetrics fm = p.getFontMetrics();
		if (tick / 10 < message.length()) {
			p.drawString(message.substring(0, tick / 10),
					Painter.width / 2 - fm.stringWidth(message.substring(0, tick / 10)) / 2,
					Painter.height / 2 + fm.getHeight() / 2);
		} else {
			p.drawString(message, Painter.width / 2 - fm.stringWidth(message) / 2,
					Painter.height / 2 + fm.getHeight() / 2);
			if (tick / 10 - message.length() < message2.length()) {
				p.drawString(message2.substring(0, tick / 10 - message.length()),
						Painter.width / 2 - fm.stringWidth(message2.substring(0, tick / 10 - message.length())) / 2,
						Painter.height / 2 + fm.getHeight() / 2 * 3);
			} else {
				p.drawString(message2, Painter.width / 2 - fm.stringWidth(message2) / 2,
						Painter.height / 2 + fm.getHeight() / 2 * 3);
			}
		}
	}

}
