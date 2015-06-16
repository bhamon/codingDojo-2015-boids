package boids.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Objects;

import javax.swing.JPanel;

import boids.Monde;

public class PanelMonde extends JPanel {

	private static final long serialVersionUID = -1092142994005046097L;

	Monde monde;

	public PanelMonde(Monde monde) {
		Objects.requireNonNull(monde);
		this.monde = monde;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		AffineTransform t = g2d.getTransform();
		t.scale(1, -1);
		t.translate(0, -getHeight());
		t.scale((double) getWidth() / monde.getLongueur(), (double) getHeight()
				/ monde.getLargeur());
		g2d.setTransform(t);

		g2d.setColor(Color.red.darker());
		monde.paint(g2d);
	}
}
