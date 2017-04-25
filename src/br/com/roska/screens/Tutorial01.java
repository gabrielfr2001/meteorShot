package br.com.roska.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import br.com.roska.main.Painter;
import br.com.roska.model.Button;

public class Tutorial01 extends Screen {

	public Image IMAGE;

	public Tutorial01() {
		super(new Rectangle(0, Painter.height / 5 * 4, Painter.width, Painter.height / 5));
		Button menuButtonMenu = new Button(Painter.width / 2 - BUTTON_WIDTH / 2, 0, BUTTON_WIDTH, BUTTON_HEIGTH,
				BUTTON_MENU, MENU_BUTTON_MENU);
		menuButtonMenu.margin = BUTTON_BORDER;
		menuButtonMenu.orientation = Button.RIGHT;
		buttons.add(menuButtonMenu);
		Button tutorialButtonProximo = new Button(Painter.width / 2 - BUTTON_WIDTH / 2, 0, BUTTON_WIDTH, BUTTON_HEIGTH,
				BUTTON_TUTORIAL2, MENU_BUTTON_TUTORIAL2);
		tutorialButtonProximo.margin = BUTTON_BORDER;
		tutorialButtonProximo.orientation = Button.RIGHT;
		buttons.add(tutorialButtonProximo);
	}

	@Override
	public void paint(Graphics2D p, Painter painter) {
		super.paint(p);
		int x = Painter.width / 2 - IMAGE.getWidth(null) / 2;
		int y = Painter.height / 2 - IMAGE.getHeight(null) / 2;
		p.setColor(Color.BLACK);
		p.fillRect(x, y, IMAGE.getWidth(null), IMAGE.getHeight(null));
		p.drawImage(IMAGE, x, y, null);
	}

}
