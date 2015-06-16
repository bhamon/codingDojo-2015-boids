package boids;

public class Vitesse extends Position implements Cloneable {

	public Vitesse(double x, double y) {
		super(x, y);
	}

	@Override
	public Vitesse clone() {
		return new Vitesse(getX(), getY());
	}

}
