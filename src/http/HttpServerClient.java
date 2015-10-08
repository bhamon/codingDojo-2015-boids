package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServerClient extends Thread {
	private final Pattern PATTERN_COMMAND = Pattern.compile("(GET) (.*?) HTTP/([0-9]\\.[0-9])");
	private final Pattern PATTERN_HEADER = Pattern.compile("(.*?): (.*?)");

	private final Socket socket;
	private final BufferedReader reader;
	private final PrintWriter writer;
	private Exception exception;

	public HttpServerClient(Socket socket) throws IOException {
		this.socket = socket;
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream(), true);
	}

	private HttpRequest readRequest() throws IOException {
		Matcher command = PATTERN_COMMAND.matcher(reader.readLine());
		HttpRequest req = new HttpRequest(command.group(1), URI.create(command.group(2)), command.group(3));

		String line = reader.readLine();
		while (!line.isEmpty()) {
			Matcher header = PATTERN_HEADER.matcher(line);
			req.setHeader(header.group(1), header.group(2));
			line = reader.readLine();
		}

		StringBuilder body = new StringBuilder();
		char buffer[] = new char[512];
		int size;
		while ((size = reader.read(buffer)) == buffer.length) {
			body.append(buffer, 0, size);
		}

		req.setBody(body.toString());

		return req;
	}

	private void writeResponse(HttpResponse res) {
		writer.print("HTTP/");
		writer.print(res.getVersion());
		writer.print(" ");
		writer.print(res.getMessage());
		writer.println();
		writer.println(res.getBody());
		writer.println();
	}

	@Override
	public void run() {
		try {
			HttpRequest request = readRequest();
			HttpResponse response = new HttpResponse("1.1");

			writeResponse(response);
		} catch (Exception ex) {
			exception = ex;
		} finally {
			try {
				socket.close();
			} catch (IOException ex) {
			}
		}
	}

	public Exception getException() {
		return exception;
	}
}
