package exceptions;

/**
 * Created by dave on 2/9/18.
 */
public class DuplicateVolunteerUserIDException extends Exception {
    private String msgString = "";

    public DuplicateVolunteerUserIDException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
