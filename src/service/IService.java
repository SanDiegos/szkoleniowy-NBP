package service;

import java.time.LocalDate;

public interface IService<D> {

	public D getParsedData(String tableType, String currencyCode, LocalDate date);

}
