package boids;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Assert;
import org.junit.Test;

import util.Point2D;
import util.Vector2D;

public class TestObjets {
	public static double PRECISION = 0.0000001;

	@Test
	public void testObjet() {
		Objet objet = new Obstacle().withPosition(new Point2D(1.123, -54.12));

		Assert.assertEquals(objet.getPosition(), new Point2D(1.123, -54.12));
	}

	@Test
	public void testParticule() {
		Particule particule = (Particule) new Particule().withVitesse(new Vector2D(1.0, 1.0)).withDistanceVision(2.5)
				.withPosition(new Point2D(0.0, 0.0));
		Assert.assertNotEquals(new Vector2D(0.0, 1.0), particule.getVitesse());
		Assert.assertEquals(particule.getVitesse(), new Vector2D(1.0, 1.0));
		Assert.assertEquals(particule.getPosition(), new Point2D(0.0, 0.0));
		Assert.assertEquals(particule.getDistanceVision(), 2.5, 0.0);
	}

	@Test
	public void testObstacle() {
		Obstacle obstacle = (Obstacle) new Obstacle().withRayon(3.14).withPosition(new Point2D(1.234, -5.2));
		assertEquals(1.234, obstacle.getPosition().x, PRECISION);
		assertEquals(-5.2, obstacle.getPosition().y, PRECISION);
		assertEquals(3.14, obstacle.getRayon(), PRECISION);

		assertNotEquals(1.8, obstacle.getPosition().x, PRECISION);
		assertNotEquals(-3.2, obstacle.getPosition().y, PRECISION);
		assertNotEquals(54.14, obstacle.getRayon(), PRECISION);
	}
}
