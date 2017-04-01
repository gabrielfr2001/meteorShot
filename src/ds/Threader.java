package ds;

public class Threader implements Runnable {
	public static final int EXPLODE = 0;
	public static final int KILL = 1;
	private Meteor m;
	private int i;

	public Threader(Meteor m, int i) {
		this.m = m;
		this.i = i;
	}

	@Override
	public void run() {
		if (i == KILL) {
			m.canadParticle = false;
			for (int i = 0; i < m.particles.size(); i++) {
				m.particles.get(i).sizeDecay = 0.3;
			}
			for (int i = 0; i < 100; i++) {
				m.vely += m.accy;
				m.y -= m.vely;
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Painter.cando = true;
		System.out.println(m.n1 * m.n2);
		Painter.meteors.remove(m);
		Painter.add = true;
	}

}
