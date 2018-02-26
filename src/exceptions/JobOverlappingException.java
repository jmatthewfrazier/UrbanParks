package exceptions;

/**
 * Created on 2/9/2018
 */

public class JobOverlappingException extends Exception {
	
	private String msgString;

    public JobOverlappingException(final String paramMsgString) {

        msgString = paramMsgString;

    }

    public String getMsgString() {
        return msgString;
    }
}
