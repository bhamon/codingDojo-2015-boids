package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestHttpServerClient {

	static HttpServer httpServer;

	@BeforeClass
	public static void creationServeur() throws UnknownHostException, IOException {
		httpServer = new HttpServer(80, 20, InetAddress.getLocalHost());
	}

	@Test
	public void testParticuleMonde() throws Exception {
		URL url = new URL("http://localhost:80/monde/particules");
		URLConnection urlConnection = url.openConnection();
		BufferedReader bfr = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

	}

}
