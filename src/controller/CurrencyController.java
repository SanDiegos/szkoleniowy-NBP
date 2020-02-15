package controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import calculations.Calculations;
import entity.currency.Currency;
import entity.tableType.Example;
import service.CurrencyService;
import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;
import util.Constants.ExchangeRatesTableTypes;

public class CurrencyController {

	private final CurrencyService currencyService = new CurrencyService();

	public Currency getExchangeRateForDate(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode,
			/* @Valid @PastOrPresent */ LocalDate date) {
		ControllerArgumentsValidator.checkIfDateIsPastOrPresent(date);
		return currencyService.getExchangeRateForDate(tableType, currencyCode, date);
	}

	public Currency getCurrentExchangeRate(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode) {
		return currencyService.getCurrentExchangeRate(tableType, currencyCode);
	}

	public Example getExchangeRatesTable(ExchangeRatesTableTypes tableType) {
		return currencyService.getCurrentExchangeRates(tableType);
	}

	public Currency getExchangeRateFromFile(String path) {
		return currencyService.getExchangeRateFromFile(() -> path);
	}

	public BigDecimal exchange(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode, BigDecimal amount) {
		Currency curr = currencyService.getCurrentExchangeRate(tableType, currencyCode);
		if (Objects.isNull(curr) || Objects.isNull(curr.getRate())) {
			throw new RuntimeException("Didn't found current course currency.");
		}
		return Calculations.exchange(amount, curr.getRate());
	}

}
