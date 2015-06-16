package boids;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestVitesse {

	@Test
	public void TestAjouterVitesse() {
		Vitesse vitesseInitiale = new Vitesse(1.0, -1.1552);
		Vitesse vitesseAAjouter = new Vitesse(12.012, 1.0);

		assertEquals(vitesseInitiale.addSpeed(vitesseAAjouter), new Vitesse(
				13.012, -0.1552));
	}

}
