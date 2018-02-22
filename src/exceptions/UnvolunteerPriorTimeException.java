package exceptions;

public class UnvolunteerPriorTimeException extends Exception {
	private String msgString;

    public UnvolunteerPriorTimeException(final String paramMsgString) {

        msgString = paramMsgString;

    }

    public String getMsgString() {
        return msgString;
    }
}
