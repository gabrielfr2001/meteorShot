package br.com.roska.listener;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import br.com.roska.main.Painter;
import br.com.roska.model.Button;

public class MouseListener implements java.awt.event.MouseListener, MouseMotionListener {

	private JFrame frame;
	private Button button;

	public MouseListener(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (Painter.currentScreen != null) {

			int x = (int) e.getPoint().getLocation().getX();
			int y = (int) e.getPoint().getLocation().getY();

			for (Button b : Painter.currentScreen.buttons) {
				if (b.verify(x, y)) {
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (Painter.currentScreen != null && Painter.popup == null) {

			int x = (int) e.getPoint().getLocation().getX();
			int y = (int) e.getPoint().getLocation().getY();

			for (Button b : Painter.currentScreen.buttons) {
				if (b.verify(x, y)) {
					button = b;
				}
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (Painter.currentScreen != null && Painter.popup == null) {

			int x = (int) e.getPoint().getLocation().getX();
			int y = (int) e.getPoint().getLocation().getY();

			for (Button b : Painter.currentScreen.buttons) {
				if (b.verify(x, y)) {
					if (button == b) {
						Painter.b = b;
						Painter.b.activated = true;
						Painter.buttonPressed();
					}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (Painter.currentScreen != null && Painter.popup == null) {

			int x = (int) e.getPoint().getLocation().getX();
			int y = (int) e.getPoint().getLocation().getY();

			boolean set = false;

			for (Button b : Painter.currentScreen.buttons) {
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

		} else if (Painter.popup == null) {
			Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
			frame.setCursor(cursor);
		}
	}

}
