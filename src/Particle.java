import java.awt.Graphics2D;
import java.awt.Image;

public class Particle {

	public double size;
	public double graphicalSize;
	public double gravity;
	public double vy;
	public static final double ACC = 0.1;
	public double x;
	public double y;
	public int id;
	public static short globalState;
	public short state;
	public static int number;
	public Image img;
	public Image img2;
	public Image img3;

	public Particle() {
		x = (Math.random() * 300) + 100;
		y = (Math.random() * 300) + 100;
		size = 2;
		graphicalSize = 10;
		gravity = Math.random();
		System.out.println(x + " " + y);
	}

	public void draw(Graphics2D p) {

		p.drawImage(img, (int) x - 3, (int) y, null);
		p.drawImage(img3, (int) x, (int) y + 23, null);
		p.drawImage(img2, (int) x - 1, (int) y + 17, null);

		/*
		 * switch (state) { case 0: p.setColor(Color.BLUE); break; case 1:
		 * p.setColor(new Color(66, 134, 244)); break; case 2: p.setColor(new
		 * Color(138, 188, 234)); break; } p.fillOval((int) (x - graphicalSize /
		 * 2), (int) (y - graphicalSize / 2), (int) graphicalSize, (int)
		 * graphicalSize);
		 */}

}
