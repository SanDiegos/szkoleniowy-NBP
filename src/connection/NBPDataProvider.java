package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import connection.validator.HTTPConnectionValidators;
import exception.ConnectionException;
import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;

public class NBPDataProvider implements IDataProvider<String> {

	public NBPDataProvider() {
	}

	@Override
	public String downloadData(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode, LocalDate date) {
		HttpURLConnection connection = httpURLConnectionCreator(tableType, currencyCode, date);
		Optional<String> response = null;
		try (BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			response = responseReader.lines().findAny();
		} catch (IOException e) {
			throw new ConnectionException(String.format("Error while reading response form connection on URL: [%s].",
					connection.getURL().getPath()), e);
		} finally {
			connection.disconnect();
		}
		return response.get();
	}

	@Override
	public boolean hasData(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode, LocalDate date) {
		HttpURLConnection connection = httpURLConnectionCreator(tableType, currencyCode, date);
		return Objects.isNull(date) ? HTTPConnectionValidators.validateConnection(connection)
				: HTTPConnectionValidators.validateConnectionWithoutThrow(connection);
	}

	private HttpURLConnection httpURLConnectionCreator(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode, LocalDate date) {

		URL url = Objects.isNull(date) ? new ExchangeRateURLEnhancer(tableType, currencyCode).getPath()
				: new ExchangeRateURLEnhancer(tableType, currencyCode, date).getPath();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			throw new ConnectionException("Error while trying to open connection via HTTP.", e);
		}
		return connection;
	}

}
