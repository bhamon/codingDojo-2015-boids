package http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HttpServer extends Thread {
	private final ServerSocket ss;
	private final List<HttpServerClient> clients;
	private boolean listening;

	public HttpServer(int port, int backlog, InetAddress address) throws IOException {
		ss = new ServerSocket(port, backlog, address);
		clients = new ArrayList<HttpServerClient>();
	}

	@Override
	public void run() {
		listening = true;
		while (listening) {
			try {
				Socket socket = ss.accept();
				HttpServerClient client = new HttpServerClient(socket);
				clients.add(client);
				client.start();
			} catch (IOException ex) {
				// Most likely from stopListening()
				continue;
			}
		}

		clients.forEach(client -> {
			try {
				client.interrupt();
				client.join();
			} catch (InterruptedException ex) {
				// We don't give a fuck ;p
			}
		});

		try {
			ss.close();
		} catch (IOException ex) {
			// Same here ;p
		}
	}

	public void stopListening() {
		interrupt();
		listening = false;
	}
}
