package exceptions;

/**
 * Created by dave on 2/17/18.
 */
public class UrbanParksSystemOperationException extends Exception {

    private String msgString = "";

    public UrbanParksSystemOperationException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
