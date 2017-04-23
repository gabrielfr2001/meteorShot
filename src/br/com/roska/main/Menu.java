package br.com.roska.main;

import java.awt.Graphics2D;

import br.com.roska.model.Button;
import br.com.roska.screens.Screen;

public class Menu extends Screen {

	public Menu() {
		Button menuButtonQuit = new Button(Painter.width / 2 - BUTTON_WIDTH / 2, 0, BUTTON_WIDTH, BUTTON_HEIGTH,
				BUTTON_QUIT, MENU_BUTTON_QUIT);
		menuButtonQuit.margin = BUTTON_BORDER;
		menuButtonQuit.orientation = Button.TOP;
		buttons.add(menuButtonQuit);
		Button menuButtonLeaderboard = new Button(Painter.width / 2 - BUTTON_WIDTH / 2, 0, BUTTON_WIDTH, BUTTON_HEIGTH,
				BUTTON_LEADERBOARD, MENU_BUTTON_LEADERBOARD);
		menuButtonLeaderboard.margin = BUTTON_BORDER;
		menuButtonLeaderboard.orientation = Button.TOP;
		buttons.add(menuButtonLeaderboard);
		Button menuButtonTutorial = new Button(Painter.width / 2 - BUTTON_WIDTH / 2, 0, BUTTON_WIDTH, BUTTON_HEIGTH,
				BUTTON_TUTORIAL, MENU_BUTTON_TUTORIAL);
		menuButtonTutorial.margin = BUTTON_BORDER;
		menuButtonTutorial.orientation = Button.TOP;
		buttons.add(menuButtonTutorial);
		Button menuButtonCredits = new Button(Painter.width / 2 - BUTTON_WIDTH / 2, 0, BUTTON_WIDTH, BUTTON_HEIGTH,
				BUTTON_CREDITS, MENU_BUTTON_CREDITS);
		menuButtonCredits.margin = BUTTON_BORDER;
		menuButtonCredits.orientation = Button.TOP;
		buttons.add(menuButtonCredits);
		Button menuButtonPlay = new Button(Painter.width / 2 - BUTTON_WIDTH / 2, 0, BUTTON_WIDTH, BUTTON_HEIGTH,
				BUTTON_PLAY, MENU_BUTTON_PLAY);
		menuButtonPlay.margin = BUTTON_BORDER;
		menuButtonPlay.orientation = Button.TOP;
		buttons.add(menuButtonPlay);
	}

	public void paint(Graphics2D p, Painter painter) {
		super.paint(p);
	}

}
