package demo;

import java.io.File;
import java.time.LocalDate;

public class FileDownloader implements DataProvider {

	String currencyDataFir = "d://currency";

	@Override
	public boolean isValid(String currency, LocalDate date) {
		// TODO Auto-generated method stub
		return new File(currencyDataFir + File.pathSeparator + date).exists();
	}

	@Override
	public String getData(String currency, LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

}
