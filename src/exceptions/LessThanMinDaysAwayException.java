package exceptions;

public class LessThanMinDaysAwayException extends Exception {

	private String msgString;

	public LessThanMinDaysAwayException() {
		this("");
	}

	public LessThanMinDaysAwayException(final String paramMsgString) {

		msgString = paramMsgString;

	}
	public String getMsgString() {
		return msgString;
	}

}
