package parser;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.tableType.Example;
import exception.ParserException;

public class HTTPtoExampleParser implements IParser<String, Example> {

	@Override
	public Example parse(String data) {
		Example parsed = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			Example[] parsedTab = mapper.readValue(data, Example[].class);
			parsed = Objects.nonNull(parsedTab) ? parsedTab[0] : null;
		} catch (IOException e) {
			throw new ParserException("Error while parsing downloaded format to POJO", e);
		}
		return parsed;
	}

}
