package util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestPoint2D {
	public static double PRECISION = 0.0000001;

	@Test
	public void testDefaultConstructor() {
		Point2D point = new Point2D();
		assertEquals(0.0, point.x, 0.0);
		assertEquals(0.0, point.y, 0.0);
	}

	@Test
	public void testConstructor() {
		Point2D point = new Point2D(125.36545, -2.0165);
		assertEquals(125.36545, point.x, PRECISION);
		assertEquals(-2.0165, point.y, PRECISION);
	}

	@Test
	public void testMinus() {
		Point2D p1 = new Point2D(-0.25421, -55615.2554);
		Point2D p2 = new Point2D(-115.21, 2536.2);
		Vector2D result = p1.minus(p2);
		assertEquals(114.95579, result.x, PRECISION);
		assertEquals(-58151.4554, result.y, PRECISION);
	}

	@Test
	public void testEquals() {
		Point2D p1 = new Point2D(25.36982, -58.214);
		Point2D p2 = new Point2D(25.36982, -58.214);
		assertEquals(p1, p2);
		assertEquals(p2, p1);
		assertEquals(p1, p1);
		assertEquals(p2, p2);
	}

	@Test
	public void testToStrng() {
		Point2D point = new Point2D(-154616.54156, 0.356416);
		assertEquals("(-154616.54156, 0.356416)", point.toString());
	}
}