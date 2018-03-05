package exceptions;

public class JobCollectionDuplicateKeyException extends Exception {
    private String msgString;

    public JobCollectionDuplicateKeyException(final String paramString) {

        this.msgString = paramString;
    }

    public String getMsgString() {
        return msgString;
    }
}
