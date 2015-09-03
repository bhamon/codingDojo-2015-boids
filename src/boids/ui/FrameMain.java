package boids.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import util.Point2D;
import util.Vector2D;
import boids.BehaviorScript;
import boids.Monde;
import boids.Obstacle;
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

		JMenuItem menuItemAnimationStart = new JMenuItem("Start");
		JMenuItem menuItemAnimationStop = new JMenuItem("Stop");

		menuItemAnimationStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		menuItemAnimationStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				animateThread.start();
				menuItemAnimationStart.setEnabled(false);
				menuItemAnimationStop.setEnabled(true);
			}
		});

		menuItemAnimationStop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
		menuItemAnimationStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				continueAnimate = false;
				menuItemAnimationStart.setEnabled(true);
				menuItemAnimationStop.setEnabled(false);
			}
		});
		menuItemAnimationStop.setEnabled(false);

		JMenuItem menuItemBehaviorLoadFromJar = new JMenuItem("Load from JAR");
		JMenuItem menuItemBehaviorLoadFromScript = new JMenuItem("Load from script");

		menuItemBehaviorLoadFromJar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
		menuItemBehaviorLoadFromScript.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK));

		JMenu menuAnimation = new JMenu("Animation");
		menuAnimation.add(menuItemAnimationStart);
		menuAnimation.add(menuItemAnimationStop);

		JMenu menuBehavior = new JMenu("Behavior");
		menuBehavior.add(menuItemBehaviorLoadFromJar);
		menuBehavior.add(menuItemBehaviorLoadFromScript);
		menuItemBehaviorLoadFromScript.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(new File("."));
				int retour = fileChooser.showOpenDialog(FrameMain.this);
				if (retour == JFileChooser.APPROVE_OPTION) {
					try {
						BehaviorScript scriptChosen = BehaviorScript.loadBehaviorFile(fileChooser.getSelectedFile().getAbsolutePath());
						FrameMain.this.monde.setBehavior(scriptChosen);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(FrameMain.this, "Une erreur s'est produite pendant le chargement : \n" + e1.getMessage());
					}
				}
			}
		});

		menuBar = new JMenuBar();
		menuBar.add(menuAnimation);
		menuBar.add(menuBehavior);
		setJMenuBar(menuBar);

		addWindowListener(this);
		getContentPane().add(panel);
	}

	public static void main(String[] args) throws Exception {
		Monde monde = new Monde(600, 600);
		monde.add(new Particule().withVitesse(new Vector2D(1.0, 0.0)).withPosition(new Point2D(50.0, 100.0)).withDistanceVision(50.0)
				.withCouleur(Color.BLUE.darker()));
		monde.add(new Particule().withVitesse(new Vector2D(-1.0, 0.0)).withPosition(new Point2D(550.0, 100.0)).withDistanceVision(50.0)
				.withCouleur(Color.RED.darker()));
		monde.add(new Particule().withVitesse(new Vector2D(1.0, 0.8)).withPosition(new Point2D(50.0, 300.0)).withDistanceVision(50.0)
				.withCouleur(Color.GREEN.darker()));
		monde.add(new Particule().withVitesse(new Vector2D(-1.0, -0.8)).withPosition(new Point2D(550.0, 300.0)).withDistanceVision(50.0)
				.withCouleur(Color.YELLOW.darker()));

		monde.add(new Obstacle().withRayon(120.0).withPosition(new Point2D(300.0, 300.0)));

		// for (int i = 0; i < 100; ++i) {
		// monde.addRandomParticule();
		// }

		FrameMain frame = new FrameMain(monde);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void run() {
		continueAnimate = true;
		while (continueAnimate) {
			try {
				monde.animer();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			repaint();

			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				break;
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
