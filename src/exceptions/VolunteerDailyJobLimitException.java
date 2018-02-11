package exceptions;

public class VolunteerDailyJobLimitException extends Exception {
    private String msgString;

    public VolunteerDailyJobLimitException() {
        this("");
    }

    public VolunteerDailyJobLimitException(final String paramMsgString) {
        msgString = paramMsgString;
    }

    public String getMsgString() {
        return msgString;
    }
}
