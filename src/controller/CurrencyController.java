package controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import calculations.Calculations;
import connection.ExchangeRateURLEnhancer;
import connection.ExchangeRatesTableURLEnchancer;
import downloader.FileDownloader;
import downloader.HTTPDownloader;
import entity.currency.Currency;
import entity.tableType.Example;
import parser.FileToCurrencyParser;
import parser.HTTPtoCurrencyParser;
import parser.HTTPtoExampleParser;
import service.CurrencyService;
import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;
import util.Constants.ExchangeRatesTableTypes;

public class CurrencyController {

	private final CurrencyService currencyService = new CurrencyService();

	public Currency getExchangeRateForDate(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode,
			/* @Valid @PastOrPresent */ LocalDate date) {
		ControllerArgumentsValidator.checkIfDateIsPastOrPresent(date);

		return currencyService.getExchangeRateForDateReparetly(new HTTPDownloader(), new HTTPtoCurrencyParser(),
				new ExchangeRateURLEnhancer(tableType, currencyCode, date), tableType, currencyCode, date);
	}

	public Currency getCurrentExchangeRate(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode) {
		return currencyService.getCurrentExchangeRate(new HTTPDownloader(), new HTTPtoCurrencyParser(),
				new ExchangeRateURLEnhancer(tableType, currencyCode));
	}

	public Example getExchangeRatesTable(ExchangeRatesTableTypes tableType) {
		Example[] mapped = currencyService.getCurrentExchangeRate(new HTTPDownloader(), new HTTPtoExampleParser(),
				new ExchangeRatesTableURLEnchancer(tableType));
		return Objects.nonNull(mapped) ? mapped[0] : null;
	}

	public Currency getExchangeRateFromFile(String path) {
		return currencyService.getExchangeRateFromFile(new FileDownloader(), new FileToCurrencyParser(), path);
	}

	public BigDecimal exchange(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode, BigDecimal amount) {
		Currency curr = currencyService.getCurrentExchangeRate(new HTTPDownloader(), new HTTPtoCurrencyParser(),
				new ExchangeRateURLEnhancer(tableType, currencyCode));
		if (Objects.isNull(curr) || Objects.isNull(curr.getRate())) {
			throw new RuntimeException("Didn't found current course currency.");
		}
		return Calculations.exchange(amount, curr.getRate());
	}

}
