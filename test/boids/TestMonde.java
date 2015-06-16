package boids;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import util.Point2D;
import util.Vector2D;

public class TestMonde {
	@Test
	public void testAddRandomParticule() {
		Monde monde = new Monde();
		Particule particule = monde.addRandomParticule();

		assertTrue(monde.contains(particule));
	}

	@Test
	public void testMondeExistantAvecUneParticule() throws OutOfBoundsException {
		Monde monde = new Monde();
		Particule particule = new Particule();
		monde.add(particule);
		assertTrue(monde.contains(particule));
	}

	@Test
	public void testParticule() {
		Particule particule = new Particule(new Vector2D(1.0, 1.0), new Point2D(0.0, 0.0), 2.5);
		Assert.assertNotEquals(new Vector2D(0.0, 1.0), particule.getVitesse());
		Assert.assertEquals(particule.getVitesse(), new Vector2D(1.0, 1.0));
		Assert.assertEquals(particule.getPosition(), new Point2D(0.0, 0.0));
		Assert.assertEquals(particule.getDistanceVision(), 2.5, 0.0);
	}

	@Test
	public void testMondeAvecDeuxParticules() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule particuleGauche = new Particule(new Vector2D(1.0, 0.0), new Point2D(5.0, 0), 1);
		Particule particuleDroite = new Particule(new Vector2D(-1.0, 0.0), new Point2D(5.0, 9), 2);
		monde.add(particuleDroite);
		monde.add(particuleGauche);
		// test ok car l'ajout des deux particules voulues ont été ajoutées
		// correctement.
	}

	@Test(expected = OutOfBoundsException.class)
	public void testHorsMonde() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule particuleHorsMonde = new Particule(new Vector2D(1.0, 0.0), new Point2D(11.0, 0), 1);
		monde.add(particuleHorsMonde);
		Assert.fail();
	}

	@Test
	public void testCheckParticule() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Point2D p = new Point2D(2.0, 0.0);
		Particule particule = new Particule(new Vector2D(1.0, 0.0), p, 1);
		monde.add(particule);
		assertTrue(monde.checkParticuleAtPosition(p));
		assertFalse(monde.checkParticuleAtPosition(new Point2D(5.0, 3.0)));
	}

	@Test
	public void testMondeAvecDeuxParticulesAffichage() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule p1 = new Particule(new Vector2D(1.0, 0.0), new Point2D(5.0, 0), 1);
		Particule p2 = new Particule(new Vector2D(-1.0, 0.0), new Point2D(5.0, 9), 2);
		monde.add(p2);
		monde.add(p1);
		monde.print();
	}

	@Test
	public void testAnimation1Etape() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule p1 = new Particule(new Vector2D(1.0, 0.0), new Point2D(0, 5), 1);
		monde.add(p1);
		monde.print();
		monde.animer();
		monde.print();
		Assert.assertTrue(monde.checkParticuleAtPosition(new Point2D(1, 5)));
	}

	@Test
	public void testGestionRebord() throws OutOfBoundsException {
		Point2D spawn = new Point2D(0, 0);
		Monde monde = new Monde(10, 10, spawn);
		Particule p1 = new Particule(new Vector2D(0.0, 1.0), new Point2D(0, 9), 1);
		monde.add(p1);
		monde.animer();
		Assert.assertTrue(monde.checkParticuleAtPosition(spawn));
	}

	@Test
	public void testIsInBounds() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Point2D p1 = new Point2D(0, 9);
		assertTrue(monde.isInBounds(p1));
		Point2D p2 = new Point2D(11, 22);
		assertFalse(monde.isInBounds(p2));
	}

	@Test
	public void testParticuleVoitAutreParticule() {
		Monde monde = new Monde(10, 10);
		Particule p1 = new Particule(new Vector2D(1.0, 0.0), new Point2D(0, 5), 1);
		Particule p2 = new Particule(new Vector2D(1.0, 0.0), new Point2D(0, 6), 2);
		// Particule p3 = new Particule(new Vitesse(1.0, 0.0), new Position(0,
		// 5));
		assertTrue(p1.particuleVoitAutreParticule(p2));

	}

	@Test
	public void testRepulsion() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule p1 = new Particule(new Vector2D(1.54, -0.97), new Point2D(3.14, 5.09), 1);
		Particule p2 = new Particule(new Vector2D(-1.1, 1.4), new Point2D(3.12, 5.99), 2);

		monde.add(p1);
		monde.add(p2);

		monde.animer();
		Particule p1m = monde.get(p1.getUuid());
		assertTrue(p1m.getVitesse().equals(new Vector2D(1.52, -0.07)));
		assertTrue(p1m.getPosition().equals(new Point2D(4.66, 5.02)));

	}

	@Test
	public void testCalculDistance() {
		Particule p1 = new Particule(new Vector2D(1.0, 0.0), new Point2D(3, 5), 1);
		Particule p2 = new Particule(new Vector2D(1.0, 0.0), new Point2D(3, 6), 2);

		assertEquals(1.0, p1.getPosition().minus(p2.getPosition()).getMagnitude(), 0);
	}

	@Test
	public void testCalculVecteurDistance() {
		Particule p1 = new Particule(new Vector2D(1.0, 0.0), new Point2D(3, 5), 1);
		Particule p2 = new Particule(new Vector2D(1.0, 0.0), new Point2D(3, 6), 2);

		assertEquals(p2.getPosition().minus(p1.getPosition()), new Vector2D(0, 1.0));
	}
}
