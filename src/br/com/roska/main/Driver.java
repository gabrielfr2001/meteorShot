package br.com.roska.main;

import javax.swing.JFrame;

import br.com.roska.listener.MouseListener;
import br.com.roska.threads.SoundThread;

public class Driver {

	private static final String WINDOW_NAME = "Omega Space Defender (OSD)";
	public static final String MAIN_MUSIC = "arcade_music.wav";
	public static final int MAIN_MUSIC_LOOP_TIME = 34;
	public static JFrame frame;

	public static void main(String[] args) {
		JFrame frame = new JFrame(WINDOW_NAME);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		frame.setVisible(true);

		while (!frame.isVisible()) {
		}

		Painter painter = new Painter(frame);
		frame.addKeyListener(painter);

		MouseListener ml = new MouseListener(frame);

		frame.getContentPane().addMouseListener(ml);
		frame.getContentPane().addMouseMotionListener(ml);

		frame.getContentPane().add(painter);
		frame.setResizable(false);
		Driver.frame = frame;

		Painter.playSound(MAIN_MUSIC, SoundThread.REPEAT, MAIN_MUSIC_LOOP_TIME);
	}
}
