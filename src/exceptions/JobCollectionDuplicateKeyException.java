package exceptions;

/**
 * Created by dave on 2/8/18.
 */
public class JobCollectionDuplicateKeyException extends Exception {
    private String msgString = "";

    public JobCollectionDuplicateKeyException(final String paramString) {

        this.msgString = paramString;
    }

    public String getMsgString() {
        return msgString;
    }
}
