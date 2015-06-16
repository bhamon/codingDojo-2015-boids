package util;

import java.util.Objects;

public final class Point2D {
	private static final double EPSILON = 0.0000001;

	public final double x;
	public final double y;

	public Point2D() {
		this(0.0, 0.0);
	}

	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D minus(Point2D other) {
		Objects.requireNonNull(other);
		return new Vector2D(x - other.x, y - other.y);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || getClass() != other.getClass()) {
			return false;
		}

		Point2D point = (Point2D) other;
		return Math.abs(x - point.x) < EPSILON && Math.abs(y - point.y) < EPSILON;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
