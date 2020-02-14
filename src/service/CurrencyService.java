package service;

import java.net.URL;

import connection.FileConnection;
import connection.HTTPConnection;
import connection.IConnection;
import connection.IPath;
import connection.validator.HTTPConnectionValidators;
import downloader.IDownloader;
import parser.IParser;

public class CurrencyService {

//	public <S, D> D getExchangeRateForDateReparetly(IDownloader<S> downloader, IParser<S, D> parser,
//			IHTTPConnectionURL url, ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode,
//			/* @Valid @PastOrPresent */ LocalDate date) {
//
//		HTTPConnection connection = new HTTPConnection(url,
//				t -> HTTPConnectionValidators.validateConnectionWithoutThrow(t));
//
//		int loop = Constants.NUMBER_OF_REPEATINGS_IN_SEARCH_FOR_DAY;
//		while (!connection.validateConnection() && loop > 0) {
//			date = date.minusDays(1);
//			--loop;
//			url = new ExchangeRateURLEnhancer(tableType, currencyCode, date);
//			connection = new HTTPConnection(url, t -> HTTPConnectionValidators.validateConnectionWithoutThrow(t));
//		}
//		S data = downloader.download((IConnection<S>) connection);
//		return parser.parse(data);
//	}

//	public <S, D> D getExchangeRateForDateReparetly(IDownloader<S> downloader, IParser<S, D> parser,
//			IConnection<S> connection) {
//		S data = downloader.download((IConnection<S>) connection);
//		return parser.parse(data);
//	}

	public <S, D> D makeRequest(IDownloader<S> downloader, IParser<S, D> parser,
			IConnection<S> connection) {
		S data = downloader.download((IConnection<S>) connection);
		return parser.parse(data);
	}

	public <S, D> D getCurrentExchangeRate(IDownloader<S> downloader, IParser<S, D> parser,
			IPath<URL> path) {

		HTTPConnection connection = new HTTPConnection(path, t -> HTTPConnectionValidators.validateConnection(t));
		connection.validateConnection();
		return parser.parse(downloader.download((IConnection<S>) connection));
	}

	public <S, D> D getExchangeRateFromFile(IDownloader<S> downloader, IParser<S, D> parser, IPath<String> path) {
		FileConnection connection = new FileConnection(path);
		connection.validateConnection();
		return parser.parse(downloader.download((IConnection<S>) connection));
	}

//	public <S, D, P> D getCurrentExchangeRate(IDownloader<S> downloader, IParser<S, D> parser, IPath<P> path) {
//
//		HTTPConnection connection = new HTTPConnection(path, t -> HTTPConnectionValidators.validateConnection(t));
//		connection.validateConnection();
//		return parser.parse(downloader.download((IConnection<S>) connection));
//	}

}
