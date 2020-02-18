import java.math.BigDecimal;
import java.time.LocalDate;

import connection.FileDataProvider;
import connection.NBPDataProvider;
import entity.currency.Currency;
import parser.StringtoCurrencyParser;
import service.CurrencyService;

public class Main {

	public static void main(String[] args) {

		CurrencyService ser1 = new CurrencyService(new StringtoCurrencyParser(), new NBPDataProvider());
		Currency exchangeRateForDate = ser1.getParsedData("A", "EUR", LocalDate.of(2020, 02, 18));
		System.out.println("exchangeRateForDate: " + exchangeRateForDate);

		CurrencyService ser2 = new CurrencyService(new StringtoCurrencyParser(), new FileDataProvider());
		Currency exchangeRateFromFile = ser2.getParsedData("A", "EUR", LocalDate.of(2020, 02, 16));
		System.out.println("exchangeRateForDate: " + exchangeRateFromFile);

		CurrencyService ser3 = new CurrencyService(new StringtoCurrencyParser(),
				new NBPDataProvider());
		BigDecimal exchange = ser3.exchange("A", "EUR", BigDecimal.valueOf(4));
		System.out.println("exchange: " + exchange);
	}

}
