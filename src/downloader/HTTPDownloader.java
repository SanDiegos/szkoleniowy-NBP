package downloader;

import connection.IConnection;

public class HTTPDownloader implements IDownloader<String> {

	@Override
	public String download(IConnection<String> connection) {
		return connection.downloadData();
	}

}
