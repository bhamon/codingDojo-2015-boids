package boids;

import java.util.Objects;

public class Vitesse extends Position {

	public Vitesse(double x, double y) {
		super(x, y);
	}

	public Vitesse addSpeed(Vitesse vitesseAAjouter) {
		Objects.requireNonNull(vitesseAAjouter);

		return new Vitesse(this.getX() + vitesseAAjouter.getX(), this.getY()
				+ vitesseAAjouter.getY());

	}
}
