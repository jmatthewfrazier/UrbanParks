package exceptions;


public class AddNewParkJobException extends Exception {

    private String msgString;

    public AddNewParkJobException(final String paramMsgString) {

        msgString = paramMsgString;

    }

    public String getMsgString() {
        return msgString;
    }
}
