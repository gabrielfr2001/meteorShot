import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) {
		JFrame frame = new JFrame("POTATO");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(200, 200, 500, 500);

		Painter painter = new Painter();

		frame.getContentPane().add(painter);
		frame.setVisible(true);
	}

}
