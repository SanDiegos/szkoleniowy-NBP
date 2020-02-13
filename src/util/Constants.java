package util;

import lombok.Getter;

public class Constants {

	public final static String FILE_PATH = "C:\\Users\\daniel.jedra\\Documents\\JSON.txt";
	public final static int NUMBER_OF_REPEATINGS_IN_SEARCH_FOR_DAY = 15;

	@Getter
	public enum NBPBaseURL {

		EXCHANGE_RATE("http://api.nbp.pl/api/exchangerates/rates/%s/%s/"),
		EXCHANGE_RATE_DATE("http://api.nbp.pl/api/exchangerates/rates/%s/%s/%s/"),
		EXCHANGE_RATES_TABLE("http://api.nbp.pl/api/exchangerates/tables/%s/");

		private String url;

		NBPBaseURL(String url) {
			this.url = url;
		}
	}

	public enum ActualExchangeRateTableTypes {
		A, C;
	}

	public enum ExchangeRatesTableTypes {
		A, B, C;
	}

	@Getter
	public enum CurrencyCode {
		SWEDISH_KORONA("SEK"), SWISS_FRANC("CHF"), EURO("EUR"), US_DOLLAR("USD");

		private String currencyCode;

		CurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}
	}

	public enum httpResponseType {
		json, xml;
	}
}
