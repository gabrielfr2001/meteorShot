package br.com.roska.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import br.com.roska.main.Painter;
import br.com.roska.model.Button;

public class Credits extends Screen {

	private static final Rectangle BUTTON_BOUNDS = new Rectangle(0, Painter.height / 3, Painter.width,
			Painter.height / 2);
	private static int POS_EACH = 50;
	private static int POS_X = 130;
	private static int POS_Y = 140;

	public Credits() {
		super(BUTTON_BOUNDS);
		Button menuButtonCredits = new Button(Painter.width / 2 - BUTTON_WIDTH / 2, 0, BUTTON_WIDTH, BUTTON_HEIGTH,
				BUTTON_MENU, MENU_BUTTON_MENU);
		menuButtonCredits.margin = BUTTON_BORDER;
		menuButtonCredits.orientation = Button.TOP;
		buttons.add(menuButtonCredits);

		POS_Y = Painter.height / 4;
		POS_X = Painter.width / 7;

	}

	@Override
	public void paint(Graphics2D p, Painter painter) {
		super.paint(p);

		int posX = POS_X;
		int posY = POS_Y;
		int each = POS_EACH;

		p.drawString("DESENVOLVEDOR E TESTER - PEDRO HENRIQUE CENTENARO", posX, posY + each * 0);
		p.drawString("DESIGNER - JOSÉ ELIAS FRANÇA", posX, posY + each * 1);
		p.drawString("DESENVOLVEDOR E COORDENADOR - GABRIEL FELIPE ROSKOWSKI", posX, posY + each * 2);
		p.drawString("ANALISTA - MATHEUS PEDRINI", posX, posY + each * 3);
		p.drawString("TESTER - JOÃO MIGUEL", posX, posY + each * 4);
		p.setColor(Color.GREEN);
		p.drawString("TURMA 2ªB COLÉGIO SAGRADA FAMÍLIA", posX, posY + each * 5);
	}

}
