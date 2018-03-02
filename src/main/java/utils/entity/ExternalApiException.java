package utils.entity;

/**
 * 对外接口执行异常
 *
 * @summary 对外接口执行异常
 * @author: nong
 * @Copyright (c) 2017, Lianjia Group All Rights Reserved.
 * @since: 2017年11月22日 13:22:46
 */
public class ExternalApiException extends Exception {
    private ExceptionType exceptionType;

    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }

    public void setExceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public enum ExceptionType {
        OK(1, "Success"),
        Error(40001, "服务异常"),
        DOWNLOAD_FILE_NOT_EXIST(41001, "下载文件不存在"),
        UPLOAD_FILE_NOT_EXIST(41002, "下载文件不存在"),
        Permission_Denied(42001, "权限不足"),
        ERROR_PARAMETER(43001, "参数格式异常"),
        INVALID_PARAMETER(43002, "无效参数"),
        ERROR_NOT_FOUND(44004, "数据不存在"),
        NotLogin(45001, "未登录"),
        SIGNATURE_VERIFICATION_FAILED(46001 ,"签名验证失败");

        private Integer code;

        private String descStr;

        ExceptionType(Integer code, String descStr) {
            this.code = code;
            this.descStr = descStr;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDescStr() {
            return descStr;
        }

        public void setDescStr(String descStr) {
            this.descStr = descStr;
        }
    }

    public ExternalApiException() {
        super();
    }

    public ExternalApiException(ExceptionType exceptionType) {
        super(exceptionType.getDescStr());
        this.exceptionType = exceptionType;
    }

    public ExternalApiException(ExceptionType exceptionType, String descStr) {
        super(descStr);
        this.exceptionType = exceptionType;
        this.exceptionType.setDescStr(exceptionType.getDescStr());
    }

    public ExternalApiException(Throwable cause) {
        super(cause);
    }

    public ExternalApiException(String message) {
        super(message);
    }

    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ExternalApiException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
