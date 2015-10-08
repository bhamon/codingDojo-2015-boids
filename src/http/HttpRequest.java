package http;

import java.net.URI;
import java.util.Objects;

public class HttpRequest extends HttpMessage {
	private final String method;
	private final URI uri;

	private String body;

	public HttpRequest(String method, URI uri, String version) {
		super(version);

		Objects.requireNonNull(method);
		Objects.requireNonNull(uri);
		Objects.requireNonNull(version);

		this.uri = uri;
		this.method = method;
	}

	public URI getURI() {
		return uri;
	}

	public String getMethod() {
		return method;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		Objects.requireNonNull(body);

		this.body = body;
	}
}
