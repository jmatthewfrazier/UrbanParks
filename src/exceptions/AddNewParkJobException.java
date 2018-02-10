package exceptions;

/**
 * Created by dave on 2/9/18.
 */
public class AddNewParkJobException extends Exception {

    private String msgString = "";

    public AddNewParkJobException(final String paramMsgString) {

        msgString = paramMsgString;

    }

    public String getMsgString() {
        return msgString;
    }
}
