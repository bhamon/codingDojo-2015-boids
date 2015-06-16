package boids.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import util.Point2D;
import util.Vector2D;
import boids.Monde;
import boids.Particule;

public class FrameMain extends JFrame implements Runnable, WindowListener {
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
		JMenuItem menuStop = new JMenuItem("Stop");

		menuStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		menuStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				animateThread.start();
				menuStart.setEnabled(false);
				menuStop.setEnabled(true);
			}
		});
		menu.add(menuStart);

		menuStop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
		menuStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				continueAnimate = false;
				menuStart.setEnabled(true);
				menuStop.setEnabled(false);
			}
		});
		menuStop.setEnabled(false);
		menu.add(menuStop);

		menuBar = new JMenuBar();
		menuBar.add(menu);
		setJMenuBar(menuBar);

		addWindowListener(this);
		getContentPane().add(panel);
	}

	public static void main(String[] args) throws Exception {
		Monde monde = new Monde(600, 600);
		monde.add(new Particule(new Vector2D(1.0, 0.0), new Point2D(50.0, 100.0), 50.0, Color.BLUE.darker()));
		monde.add(new Particule(new Vector2D(-1.0, 0.0), new Point2D(550.0, 100.0), 50.0, Color.RED.darker()));
		monde.add(new Particule(new Vector2D(1.0, 0.8), new Point2D(50.0, 300.0), 50.0, Color.GREEN.darker()));
		monde.add(new Particule(new Vector2D(-1.0, -0.8), new Point2D(550.0, 300.0), 50.0, Color.YELLOW.darker()));

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
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		continueAnimate = false;
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
}
