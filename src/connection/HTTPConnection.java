package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Optional;
import java.util.function.Function;

public class HTTPConnection implements IConnection<String> {

	private final HttpURLConnection connection;
	private final Function<HttpURLConnection, Boolean> httpConnectionValidator;

	public HTTPConnection(IHTTPConnectionURL url, Function<HttpURLConnection, Boolean> validation) {
		this.connection = httpURLConnectionCreator(url);
		this.httpConnectionValidator = validation;
	}

	@Override
	public String downloadData() {
		Optional<String> response = null;
		try (BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			response = responseReader.lines().findAny();
		} catch (IOException e) {
			System.err.println(String.format("Error while reading response form connection on URL: [%s].",
					connection.getURL().getPath()));
			return null;
		} finally {
			connection.disconnect();
		}
		return response.get();
	}

	@Override
	public boolean validateConnection() {
		return httpConnectionValidator.apply(connection);
	}

//	@Override
//	public InputStream downloadData() {
//		try (InputStream inputStream = connection.getInputStream()) {
//			return inputStream;
//		} catch (IOException e) {
//			throw new RuntimeException(String.format("Error while reading response form connection on URL: [%s].",
//					connection.getURL().getPath()));
//		} finally {
//			connection.disconnect();
//		}
//
//	}

	private HttpURLConnection httpURLConnectionCreator(IHTTPConnectionURL u) {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) u.getUrl().openConnection();
		} catch (IOException e) {
			System.err.println("Error while trying to open connection via HTTP.");
		}
		return connection;
	}

}
