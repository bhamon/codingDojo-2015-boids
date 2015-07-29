package boids;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.UUID;

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
		Particule particule = new Particule().withVitesse(new Vector2D(1.0, 1.0)).withPosition(new Point2D(0.0, 0.0)).withDistanceVision(2.5);
		Assert.assertNotEquals(new Vector2D(0.0, 1.0), particule.getVitesse());
		Assert.assertEquals(particule.getVitesse(), new Vector2D(1.0, 1.0));
		Assert.assertEquals(particule.getPosition(), new Point2D(0.0, 0.0));
		Assert.assertEquals(particule.getDistanceVision(), 2.5, 0.0);
	}

	@Test
	public void testMondeAvecDeuxParticules() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule particuleGauche = new Particule().withVitesse(new Vector2D(1.0, 0.0)).withPosition(new Point2D(5.0, 0)).withDistanceVision(1);
		Particule particuleDroite = new Particule().withVitesse(new Vector2D(-1.0, 0.0)).withPosition(new Point2D(5.0, 9)).withDistanceVision(2);
		monde.add(particuleDroite);
		monde.add(particuleGauche);
		// test ok car l'ajout des deux particules voulues ont été ajoutées
		// correctement.
	}

	@Test(expected = OutOfBoundsException.class)
	public void testHorsMonde() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule particuleHorsMonde = new Particule().withVitesse(new Vector2D(1.0, 0.0)).withPosition(new Point2D(11.0, 0)).withDistanceVision(1);
		monde.add(particuleHorsMonde);
		Assert.fail();
	}

	@Test
	public void testCheckParticule() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Point2D p = new Point2D(2.0, 0.0);
		Particule particule = new Particule().withVitesse(new Vector2D(1.0, 0.0)).withPosition(p).withDistanceVision(1);
		monde.add(particule);
		assertTrue(monde.checkParticuleAtPosition(p));
		assertFalse(monde.checkParticuleAtPosition(new Point2D(5.0, 3.0)));
	}

	@Test
	public void testMondeAvecDeuxParticulesAffichage() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule p1 = new Particule().withVitesse(new Vector2D(1.0, 0.0)).withPosition(new Point2D(5.0, 0)).withDistanceVision(1);
		Particule p2 = new Particule().withVitesse(new Vector2D(-1.0, 0.0)).withPosition(new Point2D(5.0, 9)).withDistanceVision(2);
		monde.add(p2);
		monde.add(p1);
		monde.print();
	}

	@Test
	public void testShadow() throws Exception {
		Monde monde = new Monde(10, 10);
		monde.setBehavior(new BehaviorExplode());
		Particule p1 = new Particule().withVitesse(new Vector2D(0.0, 1.0)).withPosition(new Point2D(5.0, 7)).withDistanceVision(1)
				.withCouleur(Color.BLACK).withShadow(true);
		Particule p2 = new Particule().withVitesse(new Vector2D(0.0, -1.0)).withPosition(new Point2D(5.0, 9)).withDistanceVision(3);
		monde.add(p2);
		monde.add(p1);

		monde.animer();

		assertEquals(new Point2D(5.0, 8.0), monde.get(p1.getUuid()).getPosition());
		assertEquals(new Point2D(5.0, 8.0), monde.get(p2.getUuid()).getPosition());
	}

	@Test
	public void testBehaviorExplode() throws Exception {
		Monde monde = new Monde(10, 10);
		monde.setBehavior(new BehaviorExplode());
		Particule p1 = new Particule().withVitesse(new Vector2D(0.0, 1.0)).withPosition(new Point2D(5.0, 5.5)).withDistanceVision(1);
		Particule p2 = new Particule().withVitesse(new Vector2D(0.0, -1.0)).withPosition(new Point2D(5.0, 9)).withDistanceVision(3);
		monde.add(p2);
		monde.add(p1);

		monde.animer();

		assertEquals(3, monde.getParticulesNb());
	}

	@Test
	public void testAnimation1Etape() throws Exception {
		Monde monde = new Monde(10, 10);
		Particule p1 = new Particule().withVitesse(new Vector2D(1.0, 0.0)).withPosition(new Point2D(0, 5)).withDistanceVision(1);
		monde.add(p1);
		monde.print();
		monde.animer();
		monde.print();
		Assert.assertTrue(monde.checkParticuleAtPosition(new Point2D(1, 5)));
	}

	@Test
	public void testGestionRebord() throws Exception {
		Point2D spawn = new Point2D(0, 0);
		Monde monde = new Monde(10, 10, spawn);

		Particule p1 = new Particule().withVitesse(new Vector2D(2.5, 1.5)).withPosition(new Point2D(0, 9)).withDistanceVision(1);
		UUID p1UUID = p1.getUuid();
		monde.add(p1);
		monde.animer();
		Vector2D vitesseApresRebondP1 = new Vector2D(2.5, -1.5);
		Assert.assertEquals(vitesseApresRebondP1, monde.get(p1UUID).getVitesse());

		Particule p2 = new Particule().withVitesse(new Vector2D(2.5, 1.5)).withPosition(new Point2D(9, 0)).withDistanceVision(1);
		UUID p2UUID = p2.getUuid();
		monde.add(p2);
		monde.animer();
		Vector2D vitesseApresRebondP2 = new Vector2D(-2.5, 1.5);
		Assert.assertEquals(vitesseApresRebondP2, monde.get(p2UUID).getVitesse());

		Particule p3 = new Particule().withVitesse(new Vector2D(2.5, 1.5)).withPosition(new Point2D(9, 9)).withDistanceVision(1);
		UUID p3UUID = p3.getUuid();
		monde.add(p3);
		monde.animer();
		Vector2D vitesseApresRebondP3 = new Vector2D(-2.5, -1.5);
		Assert.assertEquals(vitesseApresRebondP3, monde.get(p3UUID).getVitesse());
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
		Particule p1 = new Particule().withVitesse(new Vector2D(1.0, 0.0)).withPosition(new Point2D(0, 5)).withDistanceVision(1);
		Particule p2 = new Particule().withVitesse(new Vector2D(1.0, 0.0)).withPosition(new Point2D(0, 6)).withDistanceVision(2);
		assertTrue(p1.particuleVoitAutreParticule(p2));

	}

	// @Test
	// public void testRepulsion() throws OutOfBoundsException {
	// Monde monde = new Monde(10, 10);
	// Particule p1 = new Particule(new Vector2D(1.54, -0.97), new Point2D(3.14,
	// 5.09), 1);
	// Particule p2 = new Particule(new Vector2D(-1.1, 1.4), new Point2D(3.12,
	// 5.99), 2);
	//
	// monde.add(p1);
	// monde.add(p2);
	//
	// monde.animer();
	// Particule p1m = monde.get(p1.getUuid());
	// assertTrue(p1m.getVitesse().equals(new Vector2D(1.52, -0.07)));
	// assertTrue(p1m.getPosition().equals(new Point2D(4.66, 5.02)));
	//
	// }

	@Test
	public void testCalculDistance() {
		Particule p1 = new Particule().withVitesse(new Vector2D(1.0, 0.0)).withPosition(new Point2D(3, 5)).withDistanceVision(1);
		Particule p2 = new Particule().withVitesse(new Vector2D(1.0, 0.0)).withPosition(new Point2D(3, 6)).withDistanceVision(2);

		assertEquals(1.0, p1.getPosition().minus(p2.getPosition()).getMagnitude(), 0);
	}

	@Test
	public void testCalculVecteurDistance() {
		Particule p1 = new Particule().withVitesse(new Vector2D(1.0, 0.0)).withPosition(new Point2D(3, 5)).withDistanceVision(1);
		Particule p2 = new Particule().withVitesse(new Vector2D(1.0, 0.0)).withPosition(new Point2D(3, 6)).withDistanceVision(2);

		assertEquals(p2.getPosition().minus(p1.getPosition()), new Vector2D(0, 1.0));
	}

	// @Test
	// public void testLoadJavascriptFile(){
	// Monde monde = new Monde(10, largeur, spawn, behavior);
	// }

	// a faire

	@Test
	public void testCommandModifySpeed() throws OutOfBoundsException {
		Monde monde = new Monde(10, 10);
		Particule p1 = new Particule().withVitesse(new Vector2D(2.5, 1.5)).withPosition(new Point2D(0, 9)).withDistanceVision(1);
		UUID p1UUID = p1.getUuid();
		monde.add(p1);
		MondeCommande mc = (MondeCommande) monde;
		mc.modifySpeed(p1, new Vector2D(1.25, 0.45));
		assertEquals(new Vector2D(1.25, 0.45), monde.get(p1UUID).getVitesse());
	}
}
