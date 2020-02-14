package connection;

import java.io.File;

import exception.ConnectionException;

public class FileConnection implements IConnection<File> {

	private final File file;

	public FileConnection(String path) {
		this.file = new File(path);
	}

	@Override
	public boolean validateConnection() {
		if (!file.exists()) {
			throw new ConnectionException(String.format("File does not exsists on directory: [%s]", file.getPath()));
		}
		return true;
	}

	@Override
	public File downloadData() {
		return file;
	}
}
