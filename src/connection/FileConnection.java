package connection;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import exception.ConnectionException;

public class FileConnection implements IConnection {

	private final File file;

	public FileConnection(String path) {
		this.file = new File(path);
	}

//	@Override
//	public String getResponseAsString() {
//		String data = null;
//		try {
//			data = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
//		} catch (IOException e) {
//			System.err.println(String.format("Error while reading the file on directory: [%s]", file.getPath()));
//		}
//		return data;
//	}

	@Override
	public boolean validateConnection() {
		if (!file.exists()) {
			throw new ConnectionException(String.format("File does not exsists on directory: [%s]", file.getPath()));
		}
		return true;
	}

	public <T> T getMapped(Class<T> type) {
		ObjectMapper objMapper = new ObjectMapper();
		T readValue = null;
		try {
			readValue = objMapper.readValue(file, type);
		} catch (JsonParseException e) {
			System.err.println("Error while parsing JSON to POJO. Probably JSON have wrong format.");
		} catch (JsonMappingException e) {
			System.err.println(
					"Error while parsing JSON to POJO. Probably JSON format doesn't match for expected JAVA class.");
		} catch (IOException e) {
			System.err.println(String.format("Error while reading the file on directory: [%s]", file.getPath()));
		}
		return readValue;
	}
}
