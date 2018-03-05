package exceptions;

public class InvalidJobLengthException extends Exception {
    private String msgString;

    public InvalidJobLengthException(final String paramString) {

        this.msgString = paramString;
    }

    public String getMsgString() {
        return msgString;
    }

}
