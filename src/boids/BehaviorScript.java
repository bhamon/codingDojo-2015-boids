package boids;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import util.Vector2D;

public class BehaviorScript implements Behavior {
	private final Behavior behavior;

	public BehaviorScript(String script) throws Exception {
		Objects.requireNonNull(script);

		ScriptEngineManager engineManager = new ScriptEngineManager();
		ScriptEngine engine = engineManager.getEngineByName("nashorn");
		Invocable invocable = (Invocable) engine;

		engine.eval(script);

		behavior = invocable.getInterface(Behavior.class);
		if (behavior == null) {
			throw new NullPointerException("Missing behavior methods");
		}
	}

	@Override
	public Vector2D computeSpeed(Particule ref, List<Particule> aoe) {
		return behavior.computeSpeed(ref, aoe);
	}

	public static void main(String[] args) throws Exception {
		Behavior b = new BehaviorScript("function computeSpeed(ref, aoe) { return ref.getVitesse(); return new Packages.util.Vector2D(2.2, 5.68); }");

		Particule ref = new Particule();
		List<Particule> aoe = new ArrayList<Particule>();
		System.out.println(b.computeSpeed(ref, aoe));
	}
}
