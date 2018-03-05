package exceptions;

public class InvalidUserException extends Exception {

    private final String msgString;

    public InvalidUserException() {
        this("");
    }

    public InvalidUserException(final String paramMsgString) {
        msgString = paramMsgString;
    }
}
