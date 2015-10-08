package http;

import java.util.HashMap;
import java.util.Map;

public class HttpMessage {
	private final String version;
	private final Map<String, String> headers;

	public HttpMessage(String version) {
		this.version = version;
		headers = new HashMap<String, String>();
	}

	public String getVersion() {
		return version;
	}

	public String getHeader(String name) {
		return headers.get(name);
	}

	public String setHeader(String name, String value) {
		return headers.put(name, value);
	}

	public String removeHeader(String name) {
		return headers.remove(name);
	}
}
