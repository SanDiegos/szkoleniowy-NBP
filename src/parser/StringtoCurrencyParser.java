package parser;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.currency.Currency;
import exception.ParserException;

public class StringtoCurrencyParser implements IParser<String, Currency> {

	@Override
	public Currency parse(String data) {
		Currency parsed = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			parsed = mapper.readValue(data, Currency.class);
		} catch (IOException e) {
			throw new ParserException("Error while parsing downloaded format to POJO", e);
		}
		return parsed;
	}


}
