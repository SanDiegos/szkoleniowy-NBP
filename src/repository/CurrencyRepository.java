package repository;

import connection.IConnection;
import downloader.IDownloader;
import parser.IParser;

public class CurrencyRepository {

	public <S, D> D makeRequest(IDownloader<S> downloader, IParser<S, D> parser,
			IConnection<S> connection) {
		S data = downloader.download(connection);
		return parser.parse(data);
	}

}
