package http;

import java.util.Objects;

public class HttpResponse extends HttpMessage {
	private int status;
	private String message;
	private StringBuilder body;

	public HttpResponse(String version) {
		super(version);

		status = 200;
		message = "OK";
		body = new StringBuilder();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		if (status < 0) {
			throw new IllegalArgumentException("Invalid [status] value");
		}

		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		Objects.requireNonNull(message);
		this.message = message;
	}

	public StringBuilder getBody() {
		return body;
	}
}
