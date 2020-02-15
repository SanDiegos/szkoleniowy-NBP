import java.math.BigDecimal;
import java.time.LocalDate;

import entity.currency.Currency;
import entity.tableType.Example;
import facade.CurencyFacade;
import util.Constants;

public class Main {

	public static void main(String[] args) {
		CurencyFacade facade = new CurencyFacade();

		Currency actualExchangeRate = facade.getCurrentExchangeRate("A", "EUR");
		System.out.println("actualExchangeRate: " + actualExchangeRate);

		Example exchangeRates = facade.getExchangeRates("C");
		System.out.println("exchangeRates: " + exchangeRates);

		Currency fromFile = facade.getExchangeRateFromFile(Constants.FILE_PATH);
		System.out.println("fromFile: " + fromFile);

		BigDecimal exchange = facade.exchange("A", "EUR", BigDecimal.valueOf(2));
		System.out.println("exchange: " + exchange);

		Currency currencyRateForDate = facade.getExchangeRateForDate("A", "EUR", LocalDate.of(2020, 02, 14));
		System.out.println("currencyRateForDate: " + currencyRateForDate);

	}

}
