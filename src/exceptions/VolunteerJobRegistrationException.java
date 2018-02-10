package exceptions;

/**
 * Created by dave on 2/9/18.
 */
public class VolunteerJobRegistrationException extends Exception {
    private String msgString = "";

    public VolunteerJobRegistrationException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
