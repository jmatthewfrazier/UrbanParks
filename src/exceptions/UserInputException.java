package exceptions;

/**
 * Created by dave on 2/17/18.
 */
public class UserInputException extends Exception {
    private String msgString = "";

    public UserInputException(final String paramString) {

        this.msgString = paramString;
    }

    public String getMsgString() {
        return msgString;
    }
}
