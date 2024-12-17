package com.codechrono.idea.plugin.utils;


import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author CodeChrono
 * 国际化工具类，项目中可调用该静态方法，为组件文本赋值
 */
public class HttpClientUtil {

    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    // utf-8字符编码
    public static final String CHARSET_UTF_8 = "utf-8";

    // HTTP内容类型。
    public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";
    public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";

    // HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";

    // HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";


    // 连接管理器
    private static PoolingHttpClientConnectionManager pool;

    // 请求配置
    private static RequestConfig requestConfig;

    static {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register(
                    "http", PlainConnectionSocketFactory.getSocketFactory()).register(
                    "https", sslsf).build();
            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            // 将最大连接数增加到2000，
            pool.setMaxTotal(2000);
            // 设置最大路由
            pool.setDefaultMaxPerRoute(2);
            // 根据默认超时限制初始化requestConfig
            int socketTimeout = 12000000;
            int connectTimeout = 12000000;
            int connectionRequestTimeout = 12000000;
            // 设置请求超时时间
            requestConfig = RequestConfig.custom()
                    .setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(connectionRequestTimeout).build();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


    }

    public static String get(String appendUrl) {
        return get("https://api.codechrono.cn/", appendUrl);
    }

    /**
     * @param url       接口地址
     * @param appendUrl 接口地址后缀
     * @return
     */
    public static String get(String url, String appendUrl) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建HttpGet实例
        HttpGet httpGet = new HttpGet(url + appendUrl);//本地调试用
        httpGet.setHeader("Content-Type", CONTENT_TYPE_TEXT_PLAIN);
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("User-Agent", "CodeChronoAgent/1.0");
        httpGet.setHeader("Accept", "*/*");
        // httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Accept-Charset", CHARSET_UTF_8);


        try {
            // 执行GET请求
            HttpResponse response = httpClient.execute(httpGet);

            // 检查响应状态码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + statusCode);
            }


            // 获取响应实体
            String responseBody = EntityUtils.toString(response.getEntity(), CHARSET_UTF_8);

            // 打印响应内容
            System.out.println("HttpClinetUtils.repos：" + responseBody);
            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
