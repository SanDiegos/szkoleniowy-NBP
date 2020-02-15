package repository;

import connection.IConnection;
import downloader.IDownloader;
import entity.IEntityHead;
import parser.IParser;

public class CurrencyRepository {

	public <S, D extends IEntityHead> D makeRequest(IDownloader<S> downloader, IParser<S, D> parser,
			IConnection<S> connection) {
		return parser.parse(downloader.download(connection));
	}

}
