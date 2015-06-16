package boids;

public class Position implements Cloneable {

	private double x;
	private double y;

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public Position clone() {
		return new Position(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		return (this.x == ((Position) obj).x && this.y == ((Position) obj).y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
