/**
 * Created by JoysonQin on 2018/3/1.
 */

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static CloseableHttpClient httpClient;

    static {
        HttpClientBuilder builder = HttpClients.custom();
        builder.setConnectionTimeToLive(30L, TimeUnit.SECONDS);
        builder.setMaxConnTotal(2000);
        builder.setMaxConnPerRoute(2000);

        builder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));

        // 保持长连接配置，需要在头添加Keep-Alive
        builder.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE);

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6"));
        headers.add(new BasicHeader("Connection", "keep-alive"));

        builder.setDefaultHeaders(headers);

        try {
            builder.setSSLContext(SSLItem.getSslContext());
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();

        builder.setDefaultRequestConfig(requestConfig);

        httpClient = builder.build();
    }

    /**
     * 简单的get请求
     *
     * @param url 目标地址
     * @return result
     */
    public static Integer doGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        try {
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                logger.debug("response status: {}", response.getStatusLine());
                Integer statusCode = response.getStatusLine().getStatusCode();
                String content = EntityUtils.toString(response.getEntity());
                return new HttpResult(statusCode, content).getStatusCode();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            httpGet.releaseConnection();
        }
        return null;
    }

    /**
     * put方式上传文件
     *
     * @param url 目标地址
     * @param file 本地物理文件
     * @param param 请求头参数
     * @return result
     */
    public static HttpResult doPut(String url, File file, Map<String, String> param) {
        HttpPut httpPut = new HttpPut(url);
        for (String head : param.keySet()) {
            httpPut.setHeader(head, param.get(head));
        }
        HttpEntity entity = null;
        try {
            entity = EntityBuilder
                    .create()
                    .setContentType(ContentType.APPLICATION_OCTET_STREAM)
                    .setFile(file)
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        httpPut.setEntity(entity);
        try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
            Integer statusCode = response.getStatusLine().getStatusCode();
            String content = getResult(response);
            return new HttpResult(statusCode, content);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            httpPut.releaseConnection();
        }
        return null;
    }

    /**
     * 字节数组方式上传文件
     *
     * @param url 目标地址
     * @param bytes 字节数组
     * @return result
     */
    public static HttpResult doPutByStream(String url, byte[] bytes) {
        ByteArrayEntity entity = new ByteArrayEntity(bytes, ContentType.APPLICATION_OCTET_STREAM);
        HttpPut httpPut = new HttpPut(url);
        httpPut.setEntity(entity);
        try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
            if (response != null) {
                Integer statusCode = response.getStatusLine().getStatusCode();
                String content = getResult(response);
                return new HttpResult(statusCode, content);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            httpPut.releaseConnection();
        }
        return null;
    }

    /**
     * 处理网络请求返回结果
     *
     * @param httpResponse response
     * @return result
     * @throws ParseException pe
     * @throws IOException ioe
     */
    private static String getResult(CloseableHttpResponse httpResponse) throws ParseException, IOException {
        String result = null;
        if (httpResponse == null) {
            return null;
        }
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            return null;
        }
        result = EntityUtils.toString(entity, Charset.defaultCharset());
        EntityUtils.consume(entity);// 关闭应该关闭的资源，适当的释放资源 ;也可以把底层的流给关闭了
        return result;
    }

    /**
     * 直接下载网络资源,重定向后的资源也可以下载下来
     *
     * @param url url
     * @return bytes
     */
    public static byte[] download(String url) {
        HttpGet httpGet = new HttpGet(url);
        try {
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (Objects.equals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK)) {
                    HttpEntity entity = response.getEntity();
                    if (entity.isStreaming()) {
                        return FileCopyUtils.copyToByteArray(entity.getContent());
                    }
                } else {
                    logger.error("download 请求失败:\n {}", EntityUtils.toString(response.getEntity()));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            httpGet.releaseConnection();
        }
        return null;
    }

    /**
     * 构造SSL参数配置
     */
    private static class SSLItem {

        private static SSLContext sslContext;
        private static X509TrustManager trustManager;

        static {
            sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSLv3");
            } catch (NoSuchAlgorithmException e) {
                logger.warn("ssl创建实例失败");
            }

            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            if (sslContext != null) {
                try {
                    sslContext.init(null, new TrustManager[] { trustManager }, null);
                } catch (KeyManagementException e) {
                    logger.warn("ssl初始化信任证书失败");
                }
            }
        }

        static SSLContext getSslContext() {
            return sslContext;
        }
    }
}

