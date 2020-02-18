package demo;

import java.time.LocalDate;

public interface DataProvider {
	public boolean isValid(String currency, LocalDate date);

	public String getData(String currency, LocalDate date);
}
