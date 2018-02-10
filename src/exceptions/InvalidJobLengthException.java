package exceptions;

/**
 * Created by dave on 2/7/18.
 */
public class InvalidJobLengthException extends Exception {
    private String msgString = "";

    public InvalidJobLengthException(final String paramString) {

        this.msgString = paramString;
    }

    public String getMsgString() {
        return msgString;
    }

}
