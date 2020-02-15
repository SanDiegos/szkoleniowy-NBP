package downloader;

import connection.IConnection;

public interface IDownloader<D> {

	D download(IConnection<D> connection);
}
