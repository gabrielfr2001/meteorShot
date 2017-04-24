package br.com.roska.main;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import br.com.roska.model.Button;
import br.com.roska.threads.Ticker;

public class Popup {

	private static final Color DEFAULT_COLOR_BACKGROUND = Color.GRAY;
	private static final Color DEFAULT_COLOR_FOREGROUND = Color.WHITE;
	private Color DEFAULT_COLOR_BORDER = Color.WHITE;
	private static final int DEFAULT_HEIGHT = Painter.height / 4;
	private static final int DEFAULT_WIDTH = Painter.width / 3;
	private static final int DEFAULT_TEXTFIELD_WIDTH = DEFAULT_WIDTH / 5 * 4;
	private static final int DEFAULT_TEXTFIELD_HEIGHT = 30;
	public static final int DEFAULT_FIELD_LENGHT = 18;

	public List<Button> buttons;
	private String head;
	public boolean textField;
	private int border;
	public String fieldText;
	private boolean activeCursor;

	public Popup(String head) {
		this.head = head;
		this.buttons = new ArrayList<>();
		this.border = 5;
		this.fieldText = "";
	}

	public void addButton(Button button) {
		buttons.add(button);
	}

	public void paint(Graphics2D g) {
		g.setColor(new Color(0, 0, 0, 130));
		g.fillRect(0, 0, Painter.width, Painter.height);
		g.setColor(DEFAULT_COLOR_BORDER);
		g.fillRect(Painter.width / 2 - DEFAULT_WIDTH / 2, Painter.height / 2 - DEFAULT_HEIGHT / 2, DEFAULT_WIDTH,
				DEFAULT_HEIGHT);
		g.setColor(DEFAULT_COLOR_BACKGROUND);
		g.fillRect(Painter.width / 2 - DEFAULT_WIDTH / 2 + border, Painter.height / 2 - DEFAULT_HEIGHT / 2 + border,
				DEFAULT_WIDTH - border * 2, DEFAULT_HEIGHT - border * 2);
		g.setFont(Painter.font2);
		FontMetrics metrics = g.getFontMetrics();
		g.setColor(DEFAULT_COLOR_FOREGROUND);

		g.drawString(head, Painter.width / 2 - metrics.stringWidth(head) / 2,
				Painter.height / 2 - DEFAULT_HEIGHT / 2 + border * 2 + metrics.getHeight());
		g.setFont(Painter.font);
		metrics = g.getFontMetrics();
		if (textField) {
			if (Ticker.frames % 30 == 0) {
				if (activeCursor) {
					activeCursor = false;
				} else {
					activeCursor = true;
				}
			}
			g.setColor(DEFAULT_COLOR_FOREGROUND);
			g.fillRect(
					Painter.width / 2 - DEFAULT_TEXTFIELD_WIDTH / 2, Painter.height / 2 - DEFAULT_HEIGHT / 2
							+ border * 2 + metrics.getHeight() + DEFAULT_TEXTFIELD_HEIGHT / 2,
					DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
			g.setColor(Color.BLACK);
			int bord = 3;
			int x = Painter.width / 2 - DEFAULT_TEXTFIELD_WIDTH / 2 + bord + metrics.stringWidth(fieldText);
			int y = Painter.height / 2 - DEFAULT_HEIGHT / 2 + border * 2 + metrics.getHeight()
					+ DEFAULT_TEXTFIELD_HEIGHT / 2 + bord;
			if (activeCursor) {
				g.drawLine(x, y, x, y + DEFAULT_TEXTFIELD_HEIGHT - bord * 2);
				g.drawLine(x + 1, y, x + 1, y + DEFAULT_TEXTFIELD_HEIGHT - bord * 2);
			}
			if (fieldText != null) {
				g.drawString(fieldText, Painter.width / 2 - DEFAULT_TEXTFIELD_WIDTH / 2 + bord,
						Painter.height / 2 - DEFAULT_HEIGHT / 2 + border * 2 + metrics.getHeight() * 2
								+ DEFAULT_TEXTFIELD_HEIGHT / 2 + bord * 2);
			}
		}

		int totalW = 0;
		for (int i = 0; i < buttons.size(); i++) {
			totalW += buttons.get(i).width + buttons.get(i).margin;
		}

		for (int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			b.x = Painter.width / 2 - totalW / 2 + (totalW / buttons.size()) * i;
			int microBorder = (DEFAULT_WIDTH - border * 2 - totalW) / 4;
			b.y = Painter.height / 2 + DEFAULT_HEIGHT / 2 - b.height - microBorder;
			b.paint(g);
		}
	}
}
