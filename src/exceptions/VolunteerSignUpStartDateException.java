package exceptions;

/**
 * Created by dave on 2/7/18.
 */
public class VolunteerSignUpStartDateException extends Exception {
    private String msgString = "";

    public VolunteerSignUpStartDateException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
