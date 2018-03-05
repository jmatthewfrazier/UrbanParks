package exceptions;

public class UrbanParksSystemOperationException extends Exception {

    private String msgString;

    public UrbanParksSystemOperationException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
