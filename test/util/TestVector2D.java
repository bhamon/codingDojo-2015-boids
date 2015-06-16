package util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestVector2D {
	public static double PRECISION = 0.0000001;

	@Test
	public void testDefaultConstructor() {
		Vector2D vector = new Vector2D();
		assertEquals(0.0, vector.x, 0.0);
		assertEquals(0.0, vector.y, 0.0);
	}

	@Test
	public void testConstructor() {
		Vector2D vector = new Vector2D(125.36545, -2.0165);
		assertEquals(125.36545, vector.x, PRECISION);
		assertEquals(-2.0165, vector.y, PRECISION);
	}

	@Test
	public void testPlus() {
		Vector2D v1 = new Vector2D(251.21, -51515.28);
		Vector2D v2 = new Vector2D(-0.1, -362.4);
		Vector2D result = v1.plus(v2);
		assertEquals(251.11, result.x, PRECISION);
		assertEquals(-51877.68, result.y, PRECISION);
	}

	@Test
	public void testMinus() {
		Vector2D v1 = new Vector2D(-0.25421, -55615.2554);
		Vector2D v2 = new Vector2D(-115.21, 2536.2);
		Vector2D result = v1.minus(v2);
		assertEquals(114.95579, result.x, PRECISION);
		assertEquals(-58151.4554, result.y, PRECISION);
	}

	@Test
	public void testMultiplyBy() {
		Vector2D vector = new Vector2D(2537.25, -62.3);
		Vector2D result = vector.multiplyBy(5.62);
		assertEquals(14259.345, result.x, PRECISION);
		assertEquals(-350.126, result.y, PRECISION);
	}

	@Test
	public void testDivideBy() {
		Vector2D vector = new Vector2D(2537.25, -62.3);
		Vector2D result = vector.divideBy(5.62);
		assertEquals(451.4679715302491, result.x, PRECISION);
		assertEquals(-11.085409252669038, result.y, PRECISION);
	}

	@Test
	public void testDot() {
		Vector2D v1 = new Vector2D(-20.6, 54.6);
		Vector2D v2 = new Vector2D(98.48, 2.03);
		assertEquals(-1917.85, v1.dot(v2), PRECISION);
		assertEquals(-1917.85, v2.dot(v1), PRECISION);
	}

	@Test
	public void testSquaredMagnitude() {
		Vector2D vector = new Vector2D(-563.21, 785.6);
		assertEquals(934372.8641, vector.getSquaredMagnitude(), PRECISION);
	}

	@Test
	public void testMagnitude() {
		Vector2D vector = new Vector2D(-563.21, 785.6);
		assertEquals(966.6296416415131, vector.getMagnitude(), PRECISION);
	}

	@Test
	public void testGetNormalized() {
		Vector2D vector = new Vector2D(-563.21, 785.6);
		Vector2D result = vector.getNormalized();
		assertEquals(-0.5826533511258427, result.x, PRECISION);
		assertEquals(0.8127207838008239, result.y, PRECISION);
		assertEquals(1.0, result.getMagnitude(), PRECISION);
	}

	@Test
	public void testTranslate() {
		Vector2D vector = new Vector2D(5151.23, 0.845);
		Point2D point = new Point2D(2.65, 7.25);
		Point2D result = vector.translate(point);
		assertEquals(5153.88, result.x, PRECISION);
		assertEquals(8.095, result.y, PRECISION);
	}

	@Test
	public void testEquals() {
		Vector2D v1 = new Vector2D(25.36982, -58.214);
		Vector2D v2 = new Vector2D(25.36982, -58.214);
		assertEquals(v1, v2);
		assertEquals(v2, v1);
		assertEquals(v1, v1);
		assertEquals(v2, v2);
	}

	@Test
	public void testToStrng() {
		Vector2D vector = new Vector2D(-154616.54156, 0.356416);
		assertEquals("(-154616.54156, 0.356416)", vector.toString());
	}
}