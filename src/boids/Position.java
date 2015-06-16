package boids;

public class Position {

	private final double x;
	private final double y;
	private final static double EPSILON = 0.000000001;

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		return ((Math.abs(this.x - ((Position) obj).x) < EPSILON) && (Math
				.abs(this.y - ((Position) obj).y) < EPSILON));
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
