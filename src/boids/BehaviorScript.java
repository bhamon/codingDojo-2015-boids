package boids;

import java.io.File;
import java.util.List;
import java.util.Objects;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.FileUtils;

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
	public void compute(Particule ref, List<Particule> aoe, MondeCommande mc) throws Exception {
		behavior.compute(ref, aoe, mc);
	}

	public static BehaviorScript loadBehaviorFile(String path) throws Exception {
		Objects.requireNonNull(path);
		String behaviorScript = FileUtils.readFileToString(new File(path));
		return new BehaviorScript(behaviorScript);
	}
}
