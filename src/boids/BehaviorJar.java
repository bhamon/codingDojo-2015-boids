package boids;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import util.JarClassLoader;

public class BehaviorJar {

	public BehaviorJar(String jar) {
		Objects.requireNonNull(jar);
	}

	public static Behavior loadBehaviorFile(String path, String className) throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		// TODO Auto-generated method stub

		try (JarClassLoader jcl = new JarClassLoader(new File(path))) {
			@SuppressWarnings("unchecked")
			Class<Behavior> behaviorClass = (Class<Behavior>) jcl.loadClass(className);
			return behaviorClass.newInstance();
		}

	}

}
