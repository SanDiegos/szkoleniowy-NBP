package parser;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.tableType.Example;
import exception.ParserException;

public class HTTPtoExampleParser implements IParser<String, Example[]> {

	@Override
	public Example[] parse(String data) {
		Example[] parsed = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			parsed = mapper.readValue(data, Example[].class);
		} catch (IOException e) {
			throw new ParserException("Error while parsing downloaded format to POJO", e);
		}
		return parsed;
	}

}
