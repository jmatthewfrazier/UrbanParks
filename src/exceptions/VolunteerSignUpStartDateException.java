package exceptions;

public class VolunteerSignUpStartDateException extends Exception {
    private String msgString;

    public VolunteerSignUpStartDateException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
