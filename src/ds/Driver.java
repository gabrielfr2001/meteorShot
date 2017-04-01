package ds;

import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) {
		JFrame frame = new JFrame("POTATO");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		frame.setVisible(true);
		Painter painter = new Painter(frame);
				frame.addKeyListener(painter);
		frame.getContentPane().add(painter);
		frame.setResizable(false);
	}
}
