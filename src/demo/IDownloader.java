package demo;

import java.time.LocalDate;

import util.Constants.CurrencyCode;

public interface IDownloader {

	String download(LocalDate date, CurrencyCode currencyCode);

}
