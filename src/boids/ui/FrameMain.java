package boids.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import boids.Monde;
import boids.Particule;
import boids.Position;
import boids.Vitesse;

public class FrameMain extends JFrame implements Runnable {
	private static final long serialVersionUID = -2781658391134900967L;

	private final PanelMonde panel;
	private final JMenuBar menuBar;
	private final Monde monde;
	private boolean continueAnimate;

	public FrameMain(Monde monde) {
		this.monde = monde;
		panel = new PanelMonde(this.monde);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		Thread animateThread = new Thread(this);

		JMenu menu = new JMenu("Animation");

		JMenuItem menuStart = new JMenuItem("Start");
		menuStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.CTRL_DOWN_MASK));
		menuStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				animateThread.start();
			}
		});
		menu.add(menuStart);

		JMenuItem menuStop = new JMenuItem("Stop");
		menuStop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				KeyEvent.CTRL_DOWN_MASK));
		menuStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				continueAnimate = false;
			}
		});
		menu.add(menuStop);

		menuBar = new JMenuBar();
		menuBar.add(menu);
		setJMenuBar(menuBar);

		getContentPane().add(panel);
	}

	public static void main(String[] args) throws Exception {
		Monde monde = new Monde();
		monde.add(new Particule(new Vitesse(1.0, 0.0), new Position(5.0, 0)));
		monde.add(new Particule(new Vitesse(-1.0, 0.0), new Position(5.0, 9)));

		FrameMain frame = new FrameMain(monde);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		continueAnimate = true;
		while (continueAnimate) {
			monde.animer();
			repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
