package downloader;

import connection.IConnection;

public interface IDownloader<S> {

	S download(IConnection<S> connection);
}
