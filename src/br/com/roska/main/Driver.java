package br.com.roska.main;

import javax.swing.JFrame;

public class Driver {

	private static final String WINDOW_NAME = "Omega Space Defender (OSD)";
	public static JFrame frame;

	public static void main(String[] args) {
		JFrame frame = new JFrame(WINDOW_NAME);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		frame.setVisible(true);

		while(!frame.isVisible()){
		}
		
		Painter painter = new Painter(frame);
		frame.addKeyListener(painter);
		
		frame.getContentPane().add(painter);
		frame.setResizable(false);
		Driver.frame = frame;
	}
}
