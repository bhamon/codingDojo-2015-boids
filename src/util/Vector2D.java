package util;

import java.util.Objects;

public final class Vector2D {
	private static final double EPSILON = 0.0000001;

	public final double x;
	public final double y;

	public Vector2D() {
		this(0.0, 0.0);
	}

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D(Point2D point) {
		this(point.x, point.y);
	}

	public Vector2D inverse() {
		return new Vector2D(-x, -y);
	}

	public Vector2D plus(Vector2D other) {
		Objects.requireNonNull(other);
		return new Vector2D(x + other.x, y + other.y);
	}

	public Vector2D minus(Vector2D other) {
		Objects.requireNonNull(other);
		return new Vector2D(x - other.x, y - other.y);
	}

	public Vector2D multiplyBy(double scalar) {
		return new Vector2D(x * scalar, y * scalar);
	}

	public Vector2D divideBy(double scalar) {
		return new Vector2D(x / scalar, y / scalar);
	}

	public double dot(Vector2D other) {
		Objects.requireNonNull(other);
		return x * other.x + y * other.y;
	}

	public double getSquaredMagnitude() {
		return x * x + y * y;
	}

	public double getMagnitude() {
		return Math.sqrt(getSquaredMagnitude());
	}

	public Vector2D getNormalized() {
		return divideBy(getMagnitude());
	}

	public Point2D translate(Point2D point) {
		Objects.requireNonNull(point);
		return new Point2D(point.x + x, point.y + y);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || getClass() != other.getClass()) {
			return false;
		}

		Vector2D vector = (Vector2D) other;
		return Math.abs(x - vector.x) < EPSILON && Math.abs(y - vector.y) < EPSILON;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
