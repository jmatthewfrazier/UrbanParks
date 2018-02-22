package exceptions;


public class InvalidJobEndDateException extends Exception {
    private String msgString;

    public InvalidJobEndDateException() {
        this("");
    }

    public InvalidJobEndDateException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
