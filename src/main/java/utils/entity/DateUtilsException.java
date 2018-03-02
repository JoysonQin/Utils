package utils.entity;

/**
 * @author dangdandan on 17/7/23.
 */
public class DateUtilsException extends  RuntimeException{

    public DateUtilsException() {


    }

    public DateUtilsException(Throwable cause) {
        super(cause);
    }

    public DateUtilsException(String message) {
        super(message);
    }

    public DateUtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    protected DateUtilsException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
