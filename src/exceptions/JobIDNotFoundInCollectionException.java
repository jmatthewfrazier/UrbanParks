package exceptions;

public class JobIDNotFoundInCollectionException extends Exception {
    private String msgString;

    public JobIDNotFoundInCollectionException() {
        this("");
    }

    public JobIDNotFoundInCollectionException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
