package boids;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import org.junit.Test;

import util.Point2D;
import util.Vector2D;

public class TestBehaviorScript {
	@Test(expected = NullPointerException.class)
	public void testNullScript() throws Exception {
		new BehaviorScript(null);
	}

	@Test(expected = ScriptException.class)
	public void testInvalidScript() throws Exception {
		new BehaviorScript("test");
	}

	@Test(expected = NullPointerException.class)
	public void testMissingMethods() throws Exception {
		new BehaviorScript("function test() { return 0; }");
	}

	@Test(expected = ClassCastException.class)
	public void testBadReturnValue() throws Exception {
		Behavior b = new BehaviorScript("function computeSpeed() { return 0; }");

		Particule ref = new Particule();
		List<Particule> aoe = new ArrayList<Particule>();
		b.computeSpeed(ref, aoe);
	}

	@Test
	public void testStaticObject() throws Exception {
		Behavior b = new BehaviorScript("function computeSpeed(ref, aoe) { return new Packages.util.Vector2D(2.2, 5.68); }");

		Particule ref = new Particule();
		List<Particule> aoe = new ArrayList<Particule>();
		Vector2D r = b.computeSpeed(ref, aoe);

		assertEquals(2.2, r.x, 0.00001);
		assertEquals(5.68, r.y, 0.00001);
	}

	@Test
	public void testObjectPropagation() throws Exception {
		Behavior b = new BehaviorScript("function computeSpeed(ref, aoe) { return ref.getVitesse(); }");

		Particule ref = new Particule(new Vector2D(2.3, 4.5), new Point2D(6.3, 8.32));
		List<Particule> aoe = new ArrayList<Particule>();
		b.computeSpeed(ref, aoe);
		Vector2D r = b.computeSpeed(ref, aoe);

		assertEquals(2.3, r.x, 0.00001);
		assertEquals(4.5, r.y, 0.00001);
	}

	@Test
	public void testLoadBehaviorFile() throws Exception {
		String path = "test/resources/test.js";
		BehaviorScript behaviorScript = BehaviorScript.loadBehaviorFile(path);
		assertNotNull(behaviorScript);
	}

}
