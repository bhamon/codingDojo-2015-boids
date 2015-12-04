package http;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.UUID;

import org.apache.http.client.fluent.Request;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.Point2D;
import util.Vector2D;
import boids.Monde;
import boids.OutOfBoundsException;
import boids.Particule;
import boids.ui.EmbeddedTomcat;

public class TestHttpServerClient {

	private Monde monde = null;
	EmbeddedTomcat embeddedTomcat = null;
	Thread tomcatThread = null;

	@Before
	public void creationServeur() throws UnknownHostException, IOException, OutOfBoundsException, CloneNotSupportedException {
		monde = new Monde();
		embeddedTomcat = new EmbeddedTomcat(monde);
		tomcatThread = new Thread(embeddedTomcat);
		tomcatThread.start();
	}

	@After
	public void killServer() throws InterruptedException {
		embeddedTomcat.stop();
		tomcatThread.join();
	}

	@Test
	public void testListParticuleMonde() throws Exception {

		UUID uuid1 = new UUID(5, 1);
		UUID uuid2 = new UUID(8, 6);

		Particule p1 = new Particule(uuid1).withVitesse(new Vector2D(0.0, 1.0)).withPosition(new Point2D(5.0, 7.5)).withDistanceVision(1);
		Particule p2 = new Particule(uuid2).withVitesse(new Vector2D(0.0, -1.0)).withPosition(new Point2D(5.0, 9)).withDistanceVision(3);
		monde.add(p2);
		monde.add(p1);

		String reponse = Request.Get("http://localhost:8080/boids/restapi/listParticules").execute().returnContent().asString();
		Assert.assertEquals("00000000-0000-0005-0000-000000000001\n00000000-0000-0008-0000-000000000006\n", reponse);

	}

	@Test
	public void testAddParticuleMonde() throws Exception {

		UUID uuid1 = new UUID(5, 1);
		String reponse = Request.Get("http://localhost:8080/boids/restapi/addParticule?uuid=" + uuid1.toString()).execute().returnContent()
				.asString();
		Assert.assertEquals("00000000-0000-0005-0000-000000000001", reponse);

		reponse = Request.Get("http://localhost:8080/boids/restapi/listParticules").execute().returnContent().asString();
		Assert.assertEquals("00000000-0000-0005-0000-000000000001\n", reponse);

	}
}
