package repository;

import connection.IConnection;
import entity.IEntityHead;
import parser.IParser;

public class CurrencyRepository {

	public <S, D extends IEntityHead> D makeRequest(IParser<S, D> parser, IConnection<S> connection) {
		return parser.parse(connection.downloadData());
	}

}
