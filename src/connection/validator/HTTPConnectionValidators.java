package connection.validator;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Optional;

import exception.ConnectionException;
import util.FunctionWithThrows;

public class HTTPConnectionValidators<T> {

	public static boolean validateConnectionWithoutThrow(HttpURLConnection connection) {
		Optional<Integer> responseCode = checkResponse(connection, HttpURLConnection::getResponseCode);
		if (HttpURLConnection.HTTP_OK != responseCode.get()) {
			System.err.println(String.format("Didn't find data in URL: [%s].", connection.getURL().getPath()));
			return false;
		}
		return true;
	}

	public static boolean validateConnection(HttpURLConnection connection) {
		Optional<Integer> responseCode = checkResponse(connection, HttpURLConnection::getResponseCode);
		Optional<String> responseMessage = checkResponse(connection, HttpURLConnection::getResponseMessage);
		if (HttpURLConnection.HTTP_OK != responseCode.get()) {
			throw new ConnectionException(String.format(
					"Bad HTTP status! [%d], Error message: [%s]. Probably wrong URL adress was used. Check the URL.",
					responseCode.get(), responseMessage.get()));
		}
		return true;
	}

	private static <T> Optional<T> checkResponse(HttpURLConnection connection,
			FunctionWithThrows<HttpURLConnection, T> getter) {
		Optional<T> response = null;
		try {
			response = Optional.of(getter.apply(connection));
		} catch (IOException e) {
			System.out
					.println(String.format("Error while getting data from URL: [%s].", connection.getURL().getPath()));
		}
		return response;
	}

}
