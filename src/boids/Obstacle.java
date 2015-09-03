package boids;

import java.awt.Color;
import java.awt.Graphics;

public class Obstacle extends Objet {

	private double rayon = 0;

	public Obstacle() {
		super();
	}

	public Obstacle withRayon(double rayon) {
		this.rayon = rayon;
		return this;
	}

	public double getRayon() {
		return rayon;
	}

	@Override
	public Obstacle clone() throws CloneNotSupportedException {

		return ((Obstacle) super.clone()).withRayon(rayon);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int posX = (int) this.getPosition().x;
		int posY = (int) this.getPosition().y;
		g.setColor(new Color(0f, 0f, 0f, 0.5f));
		// g.fillRect(posX, posY, 1, 1);
		int r2 = (int) rayon;
		g.fillOval(posX - r2, posY - r2, r2, r2);
	}
}
