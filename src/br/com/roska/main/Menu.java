package br.com.roska.main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Menu {

	public void paint(Graphics2D p, Painter painter) {
		painter.paintBackground(p);
		p.setColor(Color.WHITE);
		p.drawString("PRESS F1 TO START THE GAME AND PAUSE", 20, 100);
	}

}
