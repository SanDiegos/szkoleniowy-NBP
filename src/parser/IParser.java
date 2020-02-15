package parser;

import entity.IEntityHead;

public interface IParser<S, D extends IEntityHead> {

	D parse(S data);
}
