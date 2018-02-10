package exceptions;

/**
 * Created by dave on 2/7/18.
 */
public class InvalidJobEndDateException extends Exception {
    private String msgString = "";

    public InvalidJobEndDateException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
