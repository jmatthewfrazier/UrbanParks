package exceptions;

/**
 * Created by dave on 2/24/18.
 */
public class InvalidUserException extends Exception {

    private final String msgString;

    public InvalidUserException() {
        this("");
    }

    public InvalidUserException(final String paramMsgString) {
        msgString = paramMsgString;
    }
}
