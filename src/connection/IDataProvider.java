package connection;

import java.time.LocalDate;

import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;

public interface IDataProvider<C> {

	C downloadData(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode, LocalDate date);

	boolean hasData(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode,
			LocalDate date);
}
