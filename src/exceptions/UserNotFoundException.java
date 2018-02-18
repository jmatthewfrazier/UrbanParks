package exceptions;

/**
 * Created by dave on 2/7/18.
 */
public class UserNotFoundException extends Exception {
    private String msgString = "";

    public UserNotFoundException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
