package service;

import java.time.LocalDate;

import connection.ExchangeRateURLEnhancer;
import connection.FileConnection;
import connection.HTTPConnection;
import connection.IConnection;
import connection.IHTTPConnectionURL;
import connection.validator.HTTPConnectionValidators;
import downloader.IDownloader;
import parser.IParser;
import util.Constants;
import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;

public class CurrencyService {

	public <S, D> D getExchangeRateForDateReparetly(IDownloader<S> downloader, IParser<S, D> parser,
			IHTTPConnectionURL url, ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode,
			/* @Valid @PastOrPresent */ LocalDate date) {

		HTTPConnection connection = new HTTPConnection(url,
				t -> HTTPConnectionValidators.validateConnectionWithoutThrow(t));

		int loop = Constants.NUMBER_OF_REPEATINGS_IN_SEARCH_FOR_DAY;
		while (!connection.validateConnection() && loop > 0) {
			date = date.minusDays(1);
			--loop;
			url = new ExchangeRateURLEnhancer(tableType, currencyCode, date);
			connection = new HTTPConnection(url, t -> HTTPConnectionValidators.validateConnectionWithoutThrow(t));
		}
		return parser.parse(downloader.download((IConnection<S>) connection));
	}

	public <S, D> D getCurrentExchangeRate(IDownloader<S> downloader, IParser<S, D> parser, IHTTPConnectionURL url) {

		HTTPConnection connection = new HTTPConnection(url, t -> HTTPConnectionValidators.validateConnection(t));
		connection.validateConnection();
		return parser.parse(downloader.download((IConnection<S>) connection));
	}

	public <S, D> D getExchangeRatesTable(IDownloader<S> downloader, IParser<S, D> parser, IHTTPConnectionURL url) {
		return getCurrentExchangeRate(downloader, parser, url);
	}

	public <S, D> D getExchangeRateFromFile(IDownloader<S> downloader, IParser<S, D> parser, String path) {
		FileConnection connection = new FileConnection(path);
		connection.validateConnection();
		return parser.parse(downloader.download((IConnection<S>) connection));
	}

}
