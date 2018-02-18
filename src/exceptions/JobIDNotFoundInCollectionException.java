package exceptions;

/**
 * Created by dave on 2/17/18.
 */
public class JobIDNotFoundInCollectionException extends Exception {
    private String msgString = "";

    public JobIDNotFoundInCollectionException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
