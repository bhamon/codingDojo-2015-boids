package boids;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMonde {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

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
		Particule particule = new Particule(new Vitesse(1, 1), new Position(0,
				0), 2.5);
		Assert.assertNotEquals(new Vitesse(0, 1), particule.getVitesse());
		Assert.assertEquals(particule.getVitesse(), new Vitesse(1, 1));
		Assert.assertEquals(particule.getPosition(), new Position(0, 0));
		Assert.assertEquals(particule.getDistanceVision(), 2.5, 0.0);
	}

	@Test
	public void testMondeAvecDeuxParticules() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule particuleGauche = new Particule(new Vitesse(1.0, 0.0),
				new Position(5.0, 0), 1);
		Particule particuleDroite = new Particule(new Vitesse(-1.0, 0.0),
				new Position(5.0, 9), 2);
		monde.add(particuleDroite);
		monde.add(particuleGauche);
		// test ok car l'ajout des deux particules voulues ont été ajoutées
		// correctement.
	}

	@Test(expected = OutOfBoundsException.class)
	public void testHorsMonde() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule particuleHorsMonde = new Particule(new Vitesse(1.0, 0.0),
				new Position(11.0, 0), 1);
		monde.add(particuleHorsMonde);
		Assert.fail();
	}

	@Test
	public void testCheckParticule() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Position p = new Position(2.0, 0.0);
		Particule particule = new Particule(new Vitesse(1.0, 0.0), p, 1);
		monde.add(particule);
		assertTrue(monde.checkParticuleAtPosition(p));
		assertFalse(monde.checkParticuleAtPosition(new Position(5.0, 3.0)));
	}

	@Test
	public void testMondeAvecDeuxParticulesAffichage()
			throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule p1 = new Particule(new Vitesse(1.0, 0.0),
				new Position(5.0, 0), 1);
		Particule p2 = new Particule(new Vitesse(-1.0, 0.0), new Position(5.0,
				9), 2);
		monde.add(p2);
		monde.add(p1);
		monde.print();
	}

	@Test
	public void testAnimation1Etape() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule p1 = new Particule(new Vitesse(1.0, 0.0), new Position(0, 5),
				1);
		monde.add(p1);
		monde.print();
		monde.animer();
		monde.print();
		Assert.assertTrue(monde.checkParticuleAtPosition(new Position(1, 5)));
	}

	@Test
	public void testGestionRebord() throws OutOfBoundsException {
		Position spawn = new Position(0, 0);
		Monde monde = new Monde(10, 10, spawn);
		Particule p1 = new Particule(new Vitesse(0.0, 1.0), new Position(0, 9),
				1);
		monde.add(p1);
		monde.animer();
		Assert.assertTrue(monde.checkParticuleAtPosition(spawn));
	}

	@Test
	public void testIsInBounds() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Position p1 = new Position(0, 9);
		assertTrue(monde.isInBounds(p1));
		Position p2 = new Position(11, 22);
		assertFalse(monde.isInBounds(p2));
	}

	@Test
	public void testParticuleVoitAutreParticule() {
		Monde monde = new Monde(10, 10);
		Particule p1 = new Particule(new Vitesse(1.0, 0.0), new Position(0, 5),
				1);
		Particule p2 = new Particule(new Vitesse(1.0, 0.0), new Position(0, 6),
				2);
		// Particule p3 = new Particule(new Vitesse(1.0, 0.0), new Position(0,
		// 5));
		assertTrue(p1.particuleVoitAutreParticule(p2));

	}

	@Test
	public void testRepulsion() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule p1 = new Particule(new Vitesse(1.54, -0.97), new Position(
				3.14, 5.09), 1);
		Particule p2 = new Particule(new Vitesse(-1.1, 1.4), new Position(3.12,
				5.99), 2);

		monde.add(p1);
		monde.add(p2);

		monde.animer();
		Particule p1m = monde.get(p1.getUuid());
		assertTrue(p1m.getVitesse().equals(new Vitesse(1.52, -0.07)));
		assertTrue(p1m.getPosition().equals(new Position(4.66, 5.02)));

	}

	@Test
	public void testCalculDistance() {
		Particule p1 = new Particule(new Vitesse(1.0, 0.0), new Position(3, 5),
				1);
		Particule p2 = new Particule(new Vitesse(1.0, 0.0), new Position(3, 6),
				2);

		assertEquals(1.0, p1.getDistance(p2), 0);
	}

	@Test
	public void testCalculVecteurDistance() {
		Particule p1 = new Particule(new Vitesse(1.0, 0.0), new Position(3, 5),
				1);
		Particule p2 = new Particule(new Vitesse(1.0, 0.0), new Position(3, 6),
				2);

		assertEquals(p1.getVecteurDistance(p2), new Vitesse(0, 1.0));
	}

}
