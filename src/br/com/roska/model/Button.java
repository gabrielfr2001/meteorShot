package br.com.roska.model;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;

public class Button {
	public static final int RIGHT = 2;
	public static final int LEFT = 0;
	public static final int TOP = 1;
	public static final int BOTTOM = 3;
	
	public int x;
	public int y;
	public int width;
	public int height;
	public int margin;
	public int radius;
	public int id;

	public Image image;

	public String text;

	public Color background;
	public Color foreground;
	public Color borderColor;

	private int fontW;
	private int fontH;
	public boolean hover;
	public int addOnHover;
	public int orientation;
	public boolean activated;

	public Button(int x, int y, int w, int h, String text, int id) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.text = text;
		this.id = id;

		this.margin = 2;
		this.radius = 0;
		this.borderColor = Color.WHITE;
		this.foreground = Color.WHITE;
		this.background = Color.GRAY;
		this.addOnHover = 50;
	}

	public Button() {
		// TODO Auto-generated constructor stub
	}

	public void paint(Graphics2D g) {
		g.setColor(borderColor);
		g.fillRoundRect(x, y, width, height, radius, radius);

		if (hover) {
			int red = background.getRed() + addOnHover <= 255 ? background.getRed() + addOnHover : 255;
			int green = background.getGreen() + addOnHover <= 255 ? background.getGreen() + addOnHover : 255;
			int blue = background.getBlue() + addOnHover <= 255 ? background.getBlue() + addOnHover : 255;

			g.setColor(new Color(red, green, blue));
		} else {
			g.setColor(background);
		}

		g.fillRoundRect(x + margin, y + margin, width - margin * 2, height - margin * 2, radius, radius);
		g.setColor(foreground);

		if (fontH == 0) {
			FontMetrics metrics = g.getFontMetrics();
			fontH = metrics.getHeight();
			fontW = metrics.stringWidth(text);
		}

		int ajustH = (int) (fontH / 4 * 3);
		int ajustW = (int) (fontW / 2);

		if (image != null) {
			g.drawImage(image, x, y, null);
		}

		g.drawString(text, x + width / 2 - ajustW, y + height / 2 + ajustH);
	}

	public boolean verify(int x, int y) {
		if (x > this.x && x < this.x + this.width) {
			if (y > this.y && y < this.y + this.height) {
				return true;
			}
		}
		return false;
	}

}
