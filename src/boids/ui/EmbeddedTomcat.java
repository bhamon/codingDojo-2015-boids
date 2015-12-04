package boids.ui;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import boids.Monde;
import boids.rest.RestApi;

public class EmbeddedTomcat implements Runnable {
	public Tomcat tomcat = new Tomcat();

	public EmbeddedTomcat(Monde monde) {
		super();
		this.monde = monde;
	}

	private Monde monde = null;

	public void run() {
		start();
	}

	public void stop() {
		try {
			tomcat.stop();
			tomcat.destroy();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() {
		try {
			String webappDirLocation = "webapp";

			// The port that we should run on can be set into an environment
			// variable
			// Look for that variable and default to 8080 if it isn't there.
			String webPort = System.getenv("PORT");
			if (webPort == null || webPort.isEmpty()) {
				webPort = "8080";
			}

			tomcat.setPort(Integer.valueOf(webPort));

			Context rootContext = tomcat.addContext("/boids", new File(webappDirLocation).getAbsolutePath());
			tomcat.addServlet(rootContext, "RestApiServlet", new RestApi(monde));
			rootContext.addServletMapping("/restapi/*", "RestApiServlet");
			System.out.println("configuring app with basedir: " + new File(webappDirLocation).getAbsolutePath());

			tomcat.start();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tomcat.getServer().await();
	}
}
