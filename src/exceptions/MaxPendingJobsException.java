package exceptions;

public class MaxPendingJobsException extends Exception {

    private String msgString;

    public MaxPendingJobsException(final String paramMsgString) {
        msgString = paramMsgString;

    }
    public String getMsgString() {
        return msgString;
    }
}
