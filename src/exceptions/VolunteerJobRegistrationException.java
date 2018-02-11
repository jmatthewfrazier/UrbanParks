package exceptions;

public class VolunteerJobRegistrationException extends Exception {
    private String msgString;

    public VolunteerJobRegistrationException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
