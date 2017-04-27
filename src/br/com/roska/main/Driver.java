package br.com.roska.main;

import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JFrame;

import br.com.roska.listener.MouseListener;
import br.com.roska.listener.PopupListener;
import br.com.roska.threads.SoundThread;

public class Driver {

	private static final String WINDOW_NAME = "Tabuada Espacial";
	public static final String MAIN_MUSIC = "arcade_music.wav";
	public static final int MAIN_MUSIC_LOOP_TIME = 34;
	private static final int HEIGHT = 768;
	private static final int WIDTH = 1366;
	public static JFrame frame;

	public static void main(String[] args) {
		JFrame frame = new JFrame(WINDOW_NAME);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		Insets screenInsets = frame.getToolkit().getScreenInsets(frame.getGraphicsConfiguration());
		Rectangle screenSize = frame.getGraphicsConfiguration().getBounds();
		Rectangle maxBounds = new Rectangle(screenInsets.left + screenSize.x, screenInsets.top + screenSize.y,
				screenSize.x + screenSize.width - screenInsets.right - screenInsets.left,
				screenSize.y + screenSize.height - screenInsets.bottom - screenInsets.top);
		frame.setMaximizedBounds(maxBounds);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		frame.setVisible(true);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Painter.logger.log(e);
		}

		Painter painter = new Painter(frame);
		frame.addKeyListener(painter);

		MouseListener ml = new MouseListener(frame);
		PopupListener pl = new PopupListener(frame);

		frame.getContentPane().addMouseListener(pl);
		frame.getContentPane().addMouseMotionListener(pl);
		frame.addKeyListener(pl);
		frame.getContentPane().addMouseListener(ml);
		frame.getContentPane().addMouseMotionListener(ml);

		frame.getContentPane().add(painter);
		frame.setResizable(false);
		Driver.frame = frame;
		ConfigurationManager cm = new ConfigurationManager();
		cm.reload();

		Painter.playSound(MAIN_MUSIC, SoundThread.REPEAT, MAIN_MUSIC_LOOP_TIME);
	}
}
