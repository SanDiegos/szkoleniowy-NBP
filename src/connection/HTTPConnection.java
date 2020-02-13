package connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HTTPConnection implements IConnection {

	private final HttpURLConnection connection;
	private final Function<HttpURLConnection, Boolean> httpConnectionValidator;

	public HTTPConnection(HTTPConnectionURL url, Function<HttpURLConnection, Boolean> validation) {
		this.httpConnectionValidator = validation;
		this.connection = httpURLConnectionCreator(url);
	}

//	@Override
//	public String getResponseAsString() {
//		Optional<String> response = null;
//		try (BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//			response = responseReader.lines().findAny();
//		} catch (IOException e) {
//			System.err.println(
//					String.format("Error while reading response form connection on URL: [%s].",
//					connection.getURL().getPath()));
//			return null;
//		} finally {
//			connection.disconnect();
//		}
//		return response.get();
//	}

	@Override
	public boolean validateConnection() {
		return httpConnectionValidator.apply(connection);
	}

	private HttpURLConnection httpURLConnectionCreator(HTTPConnectionURL u) {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) u.getUrl().openConnection();
		} catch (IOException e) {
			System.err.println("Error while trying to open connection via HTTP.");
		}
		return connection;
	}

	public <T> T getMapped(Class<T> type) {
		T value = null;
		try (InputStream inputStream = connection.getInputStream()) {
			ObjectMapper objMapper = new ObjectMapper();
			value = objMapper.readValue(inputStream, type);
		} catch (IOException e) {
			throw new RuntimeException(String.format("Error while reading response form connection on URL: [%s].",
					connection.getURL().getPath()));
//			System.err.println(String.format("Error while reading response form connection on URL: [%s].",
//					connection.getURL().getPath()));
		} finally {
			connection.disconnect();
		}

		return value;
	}

}
