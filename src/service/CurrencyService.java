package service;

import java.time.LocalDate;

import connection.ExchangeRateURLEnhancer;
import connection.ExchangeRatesTableURLEnchancer;
import connection.FileConnection;
import connection.HTTPConnection;
import connection.IPath;
import connection.validator.HTTPConnectionValidators;
import downloader.FileDownloader;
import downloader.HTTPDownloader;
import entity.currency.Currency;
import entity.tableType.Example;
import parser.FileToCurrencyParser;
import parser.HTTPtoExampleParser;
import parser.StringtoCurrencyParser;
import repository.CurrencyRepository;
import util.Constants;
import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;
import util.Constants.ExchangeRatesTableTypes;

public class CurrencyService {

	private final CurrencyRepository currencyRepository = new CurrencyRepository();

	public Currency getExchangeRateForDate(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode,
			LocalDate date) {

		HTTPConnection connection = new HTTPConnection(new ExchangeRateURLEnhancer(tableType, currencyCode, date),
				t -> HTTPConnectionValidators.validateConnectionWithoutThrow(t));

		int loop = Constants.NUMBER_OF_REPEATINGS_IN_SEARCH_FOR_DAY;
		while (!connection.validateConnection() && loop > 0) {
			date = date.minusDays(1);
			--loop;
			connection = new HTTPConnection(new ExchangeRateURLEnhancer(tableType, currencyCode, date),
					t -> HTTPConnectionValidators.validateConnectionWithoutThrow(t));
		}

		return currencyRepository.makeRequest(new HTTPDownloader(), new StringtoCurrencyParser(), connection);
	}

	public Currency getCurrentExchangeRate(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode) {

		HTTPConnection connection = new HTTPConnection(new ExchangeRateURLEnhancer(tableType, currencyCode, null),
				t -> HTTPConnectionValidators.validateConnection(t));
		connection.validateConnection();
		return currencyRepository.makeRequest(new HTTPDownloader(), new StringtoCurrencyParser(), connection);
	}

	public Example getCurrentExchangeRates(ExchangeRatesTableTypes tableType) {

		HTTPConnection connection = new HTTPConnection(new ExchangeRatesTableURLEnchancer(tableType),
				t -> HTTPConnectionValidators.validateConnection(t));
		connection.validateConnection();
		return currencyRepository.makeRequest(new HTTPDownloader(), new HTTPtoExampleParser(), connection);
	}

	public Currency getExchangeRateFromFile(IPath<String> path) {
		FileConnection connection = new FileConnection(path);
		connection.validateConnection();
		return currencyRepository.makeRequest(new FileDownloader(), new FileToCurrencyParser(),
				new FileConnection(path));
	}

//	public <S, D, P> D getCurrentExchangeRate(IDownloader<S> downloader, IParser<S, D> parser, IPath<P> path) {
//
//		HTTPConnection connection = new HTTPConnection(path, t -> HTTPConnectionValidators.validateConnection(t));
//		connection.validateConnection();
//		return parser.parse(downloader.download((IConnection<S>) connection));
//	}

}
