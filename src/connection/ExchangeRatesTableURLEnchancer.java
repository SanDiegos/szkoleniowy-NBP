package connection;

import java.net.MalformedURLException;
import java.net.URL;

import lombok.Getter;
import util.Constants.ExchangeRatesTableTypes;
import util.Constants.NBPBaseURL;

@Getter
public class ExchangeRatesTableURLEnchancer implements HTTPConnectionURL {

	private URL url;

	public ExchangeRatesTableURLEnchancer(ExchangeRatesTableTypes tableType) {
		String urlAsString = String.format(NBPBaseURL.EXCHANGE_RATES_TABLE.getUrl(), tableType.toString());
		try {
			this.url = new URL(urlAsString);
		} catch (MalformedURLException e) {
			System.err.println(String.format("Error while creating URL from String: [%s]", urlAsString));
		}
	}
}