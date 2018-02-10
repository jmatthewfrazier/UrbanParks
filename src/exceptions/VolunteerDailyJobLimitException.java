package exceptions;

/**
 * Created by dave on 2/7/18.
 */
public class VolunteerDailyJobLimitException extends Exception {
    private String msgString = "";

    public VolunteerDailyJobLimitException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
