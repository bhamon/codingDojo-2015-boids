package boids;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import org.junit.Test;

import util.Vector2D;

public class TestBehaviorScript {

	public static MondeCommande mc = new MondeCommande() {
		@Override
		public void modifySpeed(Particule p1, Vector2D vector2d) {
		}

		@Override
		public void add(Particule particule) throws OutOfBoundsException {
		}
	};

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

	@Test
	public void testStaticObject() throws Exception {
		Behavior b = new BehaviorScript("function compute(ref, aoe, mc) { mc.modifySpeed(ref, new Packages.util.Vector2D(2.2, 5.68)); }");

		Particule ref = new Particule();
		Monde monde = new Monde();
		monde.add(ref);

		List<Particule> aoe = new ArrayList<Particule>();
		b.compute(ref, aoe, monde);

		Vector2D r = monde.get(ref.getUuid()).getVitesse();

		assertEquals(2.2, r.x, 0.00001);
		assertEquals(5.68, r.y, 0.00001);
	}

	@Test
	public void testLoadBehaviorFile() throws Exception {
		String path = "test/resources/test.js";
		BehaviorScript behaviorScript = BehaviorScript.loadBehaviorFile(path);
		assertNotNull(behaviorScript);
	}

}
