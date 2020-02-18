package connection;

import java.time.LocalDate;

import util.Constants.ActualExchangeRateTableTypes;
import util.Constants.CurrencyCode;

public class FTPConn implements IDataProvider<String> {

	@Override
	public String downloadData(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode, LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasData(ActualExchangeRateTableTypes tableType, CurrencyCode currencyCode, LocalDate date) {
		// TODO Auto-generated method stub
		return false;
	}


}
