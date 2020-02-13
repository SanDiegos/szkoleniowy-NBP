package demo;

import java.time.LocalDate;

import entity.currency.Currency;
import util.Constants.CurrencyCode;

public class CurrencyService {

	private final IParser parser;
	private final IDownloader downloader;

	public CurrencyService(IParser parser, IDownloader downloader) {
		this.parser = parser;
		this.downloader = downloader;
	}

	public Currency makeRequest(LocalDate date, CurrencyCode currencyCode) {
//		walidate Input
		Currency parse = parser.parse(downloader.download(date, currencyCode));
		return null;
	}
}
