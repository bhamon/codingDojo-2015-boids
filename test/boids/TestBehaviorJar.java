package boids;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

public class TestBehaviorJar {

	@Test(expected = NullPointerException.class)
	public void testNullJar() throws Exception {
		new BehaviorJar(null);
	}

	@Test
	public void testLoadJar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		String path = "test/resources/test.jar";
		String className = "boids.Behavior1";
		Behavior behavior = BehaviorJar.loadBehaviorFile(path, className);
		assertNotNull(behavior);

	}

}
