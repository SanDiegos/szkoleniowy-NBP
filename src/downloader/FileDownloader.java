package downloader;

import java.io.File;

import connection.IConnection;

public class FileDownloader implements IDownloader<File> {

	@Override
	public File download(IConnection<File> connection) {
		return connection.downloadData();
	}

}
