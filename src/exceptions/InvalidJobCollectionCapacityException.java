package exceptions;

public class InvalidJobCollectionCapacityException extends Exception {

	private final String msg;

	public InvalidJobCollectionCapacityException() {
		this("");
	}

	public InvalidJobCollectionCapacityException(final String msg) {
		this.msg = msg;
	}
}
