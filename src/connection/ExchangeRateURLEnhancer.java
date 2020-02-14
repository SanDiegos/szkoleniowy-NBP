package connection;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import lombok.Getter;
import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;
import util.Constants.NBPBaseURL;

@Getter
public class ExchangeRateURLEnhancer implements IHTTPConnectionURL {

	private URL path;

	public ExchangeRateURLEnhancer(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode) {
		String urlAsString = String.format(NBPBaseURL.EXCHANGE_RATE.getUrl(), tableType.toString(),
				currencyCode.getCurrencyCode());
		try {
			this.path = new URL(urlAsString);
		} catch (MalformedURLException e) {
			System.err.println(String.format("Error while creating URL from String: [%s]", urlAsString));
		}
	}

	public ExchangeRateURLEnhancer(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode, LocalDate date) {
		String urlAsString = String.format(NBPBaseURL.EXCHANGE_RATE_DATE.getUrl(), tableType.toString(),
				currencyCode.getCurrencyCode(), date.toString());
		try {
			this.path = new URL(urlAsString);
		} catch (MalformedURLException e) {
			System.err.println(String.format("Error while creating URL from String: [%s]", urlAsString));
		}
	}

}
