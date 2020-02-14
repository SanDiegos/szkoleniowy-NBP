package parser;

public interface IParser<S, D> {

	D parse(S data);
}
