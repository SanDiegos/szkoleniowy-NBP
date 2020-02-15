package service;

import java.time.LocalDate;

import downloader.IDownloader;
import entity.IEntity;
import parser.IParser;
import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;

public interface IService<S, D extends IEntity> {

	D makeRequest(IDownloader<S> downloader, IParser<S, D> parser, ActualExchangeRateTableTypes tableType,
			CurrencyCode currencyCode, LocalDate date);

}
