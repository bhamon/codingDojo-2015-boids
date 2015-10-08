package boids;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

import annotations.ApiObject;
import annotations.ApiResource;

public class TestUtils {

	@Test
	public void TestFindClasses() throws IOException, ClassNotFoundException {
		String packageName = "boids";
		Class<? extends Annotation> annotation = ApiObject.class;
		List<Class<?>> listeClass = Utils.findClasses(packageName, annotation);
		assertEquals(1, listeClass.size());

	}

	@Test
	public void TestFindMethods() throws IOException, ClassNotFoundException {
		Class<?> className = Monde.class;
		Class<? extends Annotation> annotation = ApiResource.class;
		List<Method> listeMethods = Utils.findMethods(className, annotation);
		assertEquals(1, listeMethods.size());

	}

}
