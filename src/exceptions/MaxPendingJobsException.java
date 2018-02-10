package exceptions;

/**
 * Created by dave on 2/7/18.
 */
public class MaxPendingJobsException extends Exception {

    private String msgString = "";

    public MaxPendingJobsException(final String paramMsgString) {
        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
