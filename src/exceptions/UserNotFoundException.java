package exceptions;

public class UserNotFoundException extends Exception {
    private String msgString;

    public UserNotFoundException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
