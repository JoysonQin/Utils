/**
 * Created by JoysonQin on 2018/3/1.
 */
public class HttpResult {

    private static final long serialVersionUID = 539897368873470013L;

    /**
     * http请求回应状态码
     */
    private Integer statusCode;

    /**
     * http请求结果
     */
    private String content;

    public HttpResult(Integer statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "statusCode=" + statusCode +
                ", content='" + content + '\'' +
                '}';
    }
}
