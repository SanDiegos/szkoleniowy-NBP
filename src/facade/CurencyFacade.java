package facade;

import java.math.BigDecimal;
import java.time.LocalDate;

import controller.CurrencyController;
import entity.currency.Currency;
import entity.tableType.Example;
import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;
import util.Constants.ExchangeRatesTableTypes;
import util.EnumUtil;

public class CurencyFacade {

	private final CurrencyController nbpController = new CurrencyController();

	public Currency getExchangeRateForDate(String tableType, String currencyCode,
			/* @Valid @PastOrPresent */ LocalDate date) {

		ActualExchangeRateTableTypes tabType = EnumUtil.getEnumByValue("tableType", tableType,
				ActualExchangeRateTableTypes.class);
		CurrencyCode currCode = EnumUtil.getEnumByValue("currencyCode", currencyCode, CurrencyCode.class);
		return nbpController.getExchangeRateForDate(tabType, currCode, date);
	}

	public Currency getCurrentExchangeRate(String tableType, String currencyCode) {

		ActualExchangeRateTableTypes tabType = EnumUtil.getEnumByValue("tableType", tableType,
				ActualExchangeRateTableTypes.class);
		CurrencyCode currCode = EnumUtil.getEnumByValue("currencyCode", currencyCode, CurrencyCode.class);
		return nbpController.getCurrentExchangeRate(tabType, currCode);
	}

	public Example getExchangeRates(String tableType) {

		ExchangeRatesTableTypes tabType = EnumUtil.getEnumByValue("tableType", tableType,
				ExchangeRatesTableTypes.class);
		return nbpController.getExchangeRatesTable(tabType);
	}

	public Currency getExchangeRateFromFile(String patch) {

		return nbpController.getExchangeRateFromFile(patch);
	}

	public BigDecimal exchange(String tableType, String currencyCode, BigDecimal amount) {

		ActualExchangeRateTableTypes tabType = EnumUtil.getEnumByValue("tableType", tableType,
				ActualExchangeRateTableTypes.class);
		CurrencyCode currCode = EnumUtil.getEnumByValue("currencyCode", currencyCode, CurrencyCode.class);
		return nbpController.exchange(tabType, currCode, amount);
	}

}
