package demo;

import entity.currency.Currency;

public interface IParser {

	Currency parse(String data);
}
