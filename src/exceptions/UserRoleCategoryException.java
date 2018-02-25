package exceptions;

/**
 * Created by dave on 2/24/18.
 */
public class UserRoleCategoryException extends Exception {
    private String msgString = "";

    public UserRoleCategoryException(final String paramMsgString) {

        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }

}
