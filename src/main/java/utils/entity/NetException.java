package utils.entity;

/**
 * @author dangdandan on 17/7/17.
 */
public class NetException extends RuntimeException {

    public NetException() {
        super();
    }

    public NetException(Throwable cause) {
        super(cause);
    }

    public NetException(String message) {
        super(message);
    }

    public NetException(String message, Throwable cause) {
        super(message, cause);
    }

    protected NetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
