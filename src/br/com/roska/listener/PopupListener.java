package br.com.roska.listener;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import br.com.roska.main.Painter;
import br.com.roska.main.Popup;
import br.com.roska.model.Button;

public class PopupListener implements MouseListener, MouseMotionListener, KeyListener {
	private static final String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRLSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%®&*()-=_+<>`¥~^][{}∞∫™ßπ≤≥£¢¨?:|.È…·¡„√ı’ÌÕ˙⁄ ";
	private JFrame frame;

	public PopupListener(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (Painter.popup != null) {

			int x = (int) e.getPoint().getLocation().getX();
			int y = (int) e.getPoint().getLocation().getY();

			for (Button b : Painter.popup.buttons) {
				if (b.verify(x, y)) {
					Painter.b = b;
					Painter.b.activated = true;
					Painter.buttonPressedPopup();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (Painter.popup != null) {

			int x = (int) e.getPoint().getLocation().getX();
			int y = (int) e.getPoint().getLocation().getY();

			boolean set = false;

			for (Button b : Painter.popup.buttons) {
				if (b.verify(x, y)) {
					set = true;
					b.hover = true;
				} else {
					b.hover = false;
				}
			}

			if (set) {
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
				frame.setCursor(cursor);
			} else {
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
				frame.setCursor(cursor);
			}

		} else if (Painter.popup != null) {
			Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
			frame.setCursor(cursor);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		boolean allowed = ALLOWED_CHARS.contains(Character.toString(c));
		if (Painter.popup != null) {
			if (allowed) {
				if (Painter.popup.fieldText.length() < Popup.DEFAULT_FIELD_LENGHT) {
					Painter.popup.fieldText += c;
				}
			}
		} else {
			if (allowed) {
				Painter.credits.field += c;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (Painter.popup != null) {
			if (e.getKeyCode() == 8 && Painter.popup.fieldText.length() > 0) {
				Painter.popup.fieldText = Painter.popup.fieldText.substring(0, Painter.popup.fieldText.length() - 1);
			} else if (e.getKeyCode() == 10) {
				if (Painter.popup == Painter.popupName) {
					Painter.b = Painter.popup.buttons.get(1);
					Painter.b.activated = true;
					Painter.buttonPressedPopup();
				}
			} else {
				System.out.println(e.getKeyCode());
			}
		} else {
			if (Painter.screen == Painter.SCREEN_CREDITS) {
				if (e.getKeyCode() == 8 && Painter.credits.field.length() > 0) {
					Painter.credits.field = Painter.credits.field.substring(0, Painter.credits.field.length() - 1);
				}
				if(Painter.credits.field.equals("batata")){
					Painter.currentScreen = Painter.easterEgg;
					Painter.screen = Painter.SCREEN_EASTER_EGG;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
