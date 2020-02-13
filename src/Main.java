
import java.math.BigDecimal;
import java.time.LocalDate;

import controller.ExchangeController;
import entity.currency.Currency;
import entity.tableType.Example;
import util.Constants;
import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;
import util.Constants.ExchangeRatesTableTypes;

public class Main {

	public static void main(String[] args) {
		ExchangeController exchangeController = new ExchangeController();

		Currency actualExchangeRate = exchangeController.getCurrentExchangeRate(ActualExchangeRateTableTypes.A,
				CurrencyCode.EURO);
		System.out.println("actualExchangeRate: " + actualExchangeRate);

		Example exchangeRates = exchangeController.getExchangeRates(ExchangeRatesTableTypes.C);
		System.out.println("exchangeRates: " + exchangeRates);

		Currency fromFile = exchangeController.getExchangeRateFromFile(Constants.FILE_PATH);
		System.out.println("fromFile: " + fromFile);

		BigDecimal exchange = exchangeController.exchange(ActualExchangeRateTableTypes.A, CurrencyCode.EURO,
				BigDecimal.valueOf(2));
		System.out.println("exchange: " + exchange);

		Currency currencyRateForDate = exchangeController.getExchangeRateForDate(ActualExchangeRateTableTypes.A,
				CurrencyCode.EURO, LocalDate.of(2020, 02, 13));
		System.out.println("currencyRateForDate: " + currencyRateForDate);
	}

}
