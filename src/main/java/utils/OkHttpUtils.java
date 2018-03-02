package utils;

import com.lianjia.plats.store.link.utils.entity.NetException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author jerry.hu 补充规约
 */
public class OkHttpUtils {

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS).build();

    public static Response syncCall(Request request) throws NetException {

        try {
            return OK_HTTP_CLIENT.newCall(request).execute();

        } catch (IOException e) {
            throw new NetException("Http服务请求异常");
        }
    }
}
