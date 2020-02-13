package util;

import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import exception.JsonUtilException;

public class JsonUtil {

	public static <T> T convertToPojo(String json, Class<T> type) {
		checkArguments(json, type);
		ObjectMapper objMapper = new ObjectMapper();
		T readValue = null;
		try {
			readValue = objMapper.readValue(json, type);
		} catch (JsonMappingException e) {
			System.err.println(
					"Error while parsing JSON to POJO. Probably JSON format doesn't match for expected JAVA class.");

		} catch (JsonProcessingException e) {
			System.err.println(
					"Error while parsing JSON to POJO. Probably JSON have wrong format.");
		}
		return readValue;
	}

	private static <T> void checkArguments(String json, Class<T> type) throws JsonUtilException {
		if (Objects.isNull(type)) {
			throw new JsonUtilException("U need to define expected type for mapper to converet JSON to POJO!");
		}
		if (Objects.isNull(json) || json.length() == 0) {
			throw new JsonUtilException("Server response data is empty. Cannot map JSON to POJO class.");
		}
	}

}
