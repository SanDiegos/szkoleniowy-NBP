package connection;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;

import lombok.Getter;
import util.Constants.CurrencyCode;
import util.Constants.NBPBaseURL;
import util.ITableType;

@Getter
public class ExchangeRateURLEnhancer implements IHTTPConnectionURL {

	private URL path;

	public ExchangeRateURLEnhancer(ITableType tableType, CurrencyCode currencyCode, LocalDate date) {
		String urlAsString = createUrlAsString(
				Objects.isNull(date) ? NBPBaseURL.EXCHANGE_RATE : NBPBaseURL.EXCHANGE_RATE_DATE, tableType,
				currencyCode, date);
		try {
			this.path = new URL(urlAsString);
		} catch (MalformedURLException e) {
			System.err.println(String.format("Error while creating URL from String: [%s]", urlAsString));
		}
	}
	
	private String createUrlAsString(NBPBaseURL baseUrl, ITableType tableType, CurrencyCode currencyCode,
			LocalDate date) {
		return String.format(baseUrl.getUrl(), tableType.toString(), currencyCode.getCurrencyCode(),
				Objects.isNull(date) ? "" : date.toString());
	}

}
