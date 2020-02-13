package controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import calculations.Calculations;
import connection.ExchangeRateURLEnhancer;
import connection.ExchangeRatesTableURLEnchancer;
import connection.FileConnection;
import connection.HTTPConnection;
import connection.validator.HTTPConnectionValidators;
import entity.currency.Currency;
import entity.tableType.Example;
import util.Constants;
import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;
import util.Constants.ExchangeRatesTableTypes;
import util.Constants.httpResponseType;

public class ExchangeController {


	public Currency getExchangeRateForDate(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode,
			/* @Valid @PastOrPresent */ LocalDate date) {
		ControllerArgumentsValidator.checkIfDateIsPastOrPresent(date);

		HTTPConnection connection = new HTTPConnection(new ExchangeRateURLEnhancer(tableType, currencyCode, date),
				t -> HTTPConnectionValidators.validateConnectionWithoutThrow(t));

		int loop = Constants.NUMBER_OF_REPEATINGS_IN_SEARCH_FOR_DAY;

		while (!connection.validateConnection() && loop > 0) {
			date = date.minusDays(1);
			--loop;
			connection = new HTTPConnection(new ExchangeRateURLEnhancer(tableType, currencyCode, date),
					t -> HTTPConnectionValidators.validateConnectionWithoutThrow(t));
		}
		return connection.getMapped(Currency.class);
	}

	public Currency getCurrentExchangeRate(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode) {
		HTTPConnection connection = new HTTPConnection(new ExchangeRateURLEnhancer(tableType, currencyCode),
				t -> HTTPConnectionValidators.validateConnection(t));
		connection.validateConnection();
		return connection.getMapped(Currency.class);
	}

	public Example getExchangeRates(ExchangeRatesTableTypes tableType) {
		HTTPConnection connection = new HTTPConnection(new ExchangeRatesTableURLEnchancer(tableType),
				t -> HTTPConnectionValidators.validateConnection(t));
		connection.validateConnection();
//		this is single Example Class object but returned from NBP API as table which contains exactly one element
		Example[] exampleMap = connection.getMapped(Example[].class);
		return Objects.nonNull(exampleMap) ? exampleMap[0] : null;
	}

	public Currency getExchangeRateFromFile(String patch) {
		FileConnection connection = new FileConnection(patch);
		connection.validateConnection();
		return connection.getMapped(Currency.class);
	}

	public BigDecimal exchange(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode, BigDecimal amount) {
		HTTPConnection connection = new HTTPConnection(new ExchangeRateURLEnhancer(tableType, currencyCode),
				t -> HTTPConnectionValidators.validateConnection(t));
		connection.validateConnection();
		Currency curr = connection.getMapped(Currency.class);
		if (Objects.isNull(curr) || Objects.isNull(curr.getRate())) {
			throw new RuntimeException("Didn't found current course currency.");
		}
		return Calculations.exchange(amount, curr.getRate());
	}

	public BigDecimal exchange(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode, BigDecimal amount,
			httpResponseType responseType) {
		HTTPConnection connection = new HTTPConnection(new ExchangeRateURLEnhancer(tableType, currencyCode),
				t -> HTTPConnectionValidators.validateConnection(t));
		connection.validateConnection();
		Currency curr = connection.getMapped(Currency.class);
		if (Objects.isNull(curr) || Objects.isNull(curr.getRate())) {
			throw new RuntimeException("Didn't found current course currency.");
		}
		return Calculations.exchange(amount, curr.getRate());
	}

}
