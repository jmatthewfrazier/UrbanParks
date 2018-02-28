package exceptions;

public class InvalidJobCollectionCapacityException extends Exception {

	private String msg;

	public InvalidJobCollectionCapacityException() {
		this("");
	}

	public InvalidJobCollectionCapacityException(String msg) {
		this.msg = msg;
	}
}
