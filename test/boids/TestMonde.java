package boids;

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
	public void testMondeExistantAvecUneParticule() throws OutOfBoundsException {
		Monde monde = new Monde();
		Particule particule = new Particule();
		monde.add(particule);
		assertTrue(monde.contains(particule));
	}

	@Test
	public void testParticule() {
		Particule particule = new Particule(new Vitesse(1, 1), new Position(0,
				0));
		Assert.assertNotEquals(new Vitesse(0, 1), particule.getVitesse());
		Assert.assertEquals(particule.getVitesse(), new Vitesse(1, 1));
		Assert.assertEquals(particule.getPosition(), new Position(0, 0));

	}

	@Test
	public void testMondeAvecDeuxParticules() throws OutOfBoundsException {
		Monde monde = new Monde(10,10);
		Particule particuleGauche = new Particule(new Vitesse(1.0, 0.0), new Position(5.0, 0));
		Particule particuleDroite = new Particule(new Vitesse(-1.0, 0.0), new Position(5.0, 9));
		monde.add(particuleDroite);
		monde.add(particuleGauche);
		// test ok car l'ajout des deux particules voulues ont été ajoutées correctement.
	}

	@Test(expected=OutOfBoundsException.class)
	public void testHorsMonde() throws OutOfBoundsException{
		Monde monde = new Monde(10,10);
		Particule particuleHorsMonde = new Particule(new Vitesse(1.0, 0.0), new Position(11.0, 0));
		monde.add(particuleHorsMonde);
		Assert.fail();		
	}
	
	@Test
	public void testMondeAvecDeuxParticulesAffichage() throws OutOfBoundsException {
		Monde monde = new Monde(10,10);
		Particule p1 = new Particule(new Vitesse(1.0, 0.0), new Position(5.0, 0));
		Particule p2 = new Particule(new Vitesse(-1.0, 0.0), new Position(5.0, 9));
		monde.add(p2);
		monde.add(p1);
		monde.print();
	}
	
	
	@Test
	public void testAnimation1Etape () throws OutOfBoundsException{
		Monde monde = new Monde(10,10);
		Particule p1 = new Particule(new Vitesse(1.0, 0.0), new Position(0, 5));
		Particule p2 = new Particule(new Vitesse(-1.0, 0.0), new Position(9, 5));
		monde.add(p2);
		monde.add(p1);
		monde.print();
		monde.animer();
		monde.print();
		Assert.assertEquals(new Position(1, 5),p1.getPosition());
		Assert.assertEquals(new Position(8, 5),p2.getPosition());
	}
}
