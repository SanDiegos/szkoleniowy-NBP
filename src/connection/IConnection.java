package connection;

public interface IConnection<C> {

	boolean validateConnection();

	C downloadData();
}
