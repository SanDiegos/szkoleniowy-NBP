package service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import calculations.Calculations;
import connection.IDataProvider;
//import demo.CurrencyService;
import entity.currency.Currency;
import parser.IParser;
import util.Constants;
import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;
import util.EnumUtil;

public class CurrencyService implements IService<Currency> {

	private IParser<String, Currency> parser;
	private IDataProvider<String> dataProvider;

	public CurrencyService(IParser<String, Currency> parser, IDataProvider<String> dataProvider) {
		this.parser = parser;
		this.dataProvider = dataProvider;
	}

	public Currency getParsedData(String tableType, String currencyCode, LocalDate date) {
		ServiceArgumentsValidator.checkIfDateIsPastOrPresent(date);
		ActualExchangeRateTableTypes tabType = EnumUtil.getEnumByValue("tableType", tableType,
				ActualExchangeRateTableTypes.class);
		CurrencyCode currCode = EnumUtil.getEnumByValue("currencyCode", currencyCode, CurrencyCode.class);

		int loop = Constants.NUMBER_OF_REPEATINGS_IN_SEARCH_FOR_DAY;
		while (!dataProvider.hasData(tabType, currCode, date) && loop > 0) {
			date = date.minusDays(1);
			--loop;
		}

		String downloadData = dataProvider.downloadData(tabType, currCode, date);
		return parser.parse(downloadData);
	}


	public BigDecimal exchange(String tableType, String currencyCode, BigDecimal amount) {

		ActualExchangeRateTableTypes tabType = EnumUtil.getEnumByValue("tableType", tableType,
				ActualExchangeRateTableTypes.class);
		CurrencyCode currCode = EnumUtil.getEnumByValue("currencyCode", currencyCode, CurrencyCode.class);

		String downloadData = dataProvider.downloadData(tabType, currCode, null);
		if (Objects.isNull(downloadData) || (downloadData.length() == 0)) {
			throw new RuntimeException("Didn't found current course currency.");
		}
		return Calculations.exchange(amount, parser.parse(downloadData).getRate());
	}

}
