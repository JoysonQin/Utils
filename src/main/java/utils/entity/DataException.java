package utils.entity;

/**
 * @author haoshuai on 2017-8-14.
 */
public class DataException extends RuntimeException {

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum DataExceptionEnum{
        OK(200, ""),
        string_error_exc(1,"参数字符串格式异常"),
        number_error_exc(2,"参数数字格式异常"),
        date_error_exc(3,"参数日期格式异常"),
        list_error_exc(4,"参数集合格式异常"),
        object_error_exc(5,"参数对象格式异常"),

        string_null_exc(11,"参数字符串为空"),
        number_null_exc(12,"参数数字为空"),
        date_null_exc(13,"参数日期为空"),
        list_null_exc(14,"参数集合为空"),
        object_null_exc(15,"参数对象为空");

        private Integer code;
        private String message;

        DataExceptionEnum() {
        }

        DataExceptionEnum(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public DataException() {
    }

    public DataException(DataExceptionEnum dataExceptionEnum) {
        this.code = dataExceptionEnum.getCode();
        this.message = dataExceptionEnum.getMessage();
    }

    public DataException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

    public DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
