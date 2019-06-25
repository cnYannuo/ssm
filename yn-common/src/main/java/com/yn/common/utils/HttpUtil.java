package com.yn.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {

    private static Log logger = LogFactory.getLog(HttpUtil.class);


    private static final String queryRegionByTaobaoUrl = "http://ip.taobao.com/service/getIpInfo.php";
    private static final String queryRegionBySinaUrl = "http://int.dpool.sina.com.cn/iplookup/iplookup.php";

    /**
     * 请求配置
     */
    private static RequestConfig requestConfig = null;

    /**
     * 连接池
     */
    private static PoolingHttpClientConnectionManager cm = null;

    /**
     * cookie管理器
     */
    // private static CookieStore cookieStore =null;

    /**
     * <p>Title:超时时间初始化和连接池 </p>
     * <p>Description: 单例模式</p>
     */
    public HttpUtil() {
        if (requestConfig == null) {
            requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectionRequestTimeout(60000).setConnectTimeout(60000).build();
            // 创建cookie store的本地实例
            // cookieStore = new BasicCookieStore();
            // 创建HttpClient上下文
            //HttpClientContext context = HttpClientContext.create();
            // context.setCookieStore(cookieStore);
        }
        if (cm == null) {
            LayeredConnectionSocketFactory sslsf = null;
            try {
                sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslsf)
                    .register("http", new PlainConnectionSocketFactory())
                    .build();
            cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setMaxTotal(200);
            cm.setDefaultMaxPerRoute(20);
        }
    }

    /**
     * @Title: doPostUTF_8 @Description:
     * 设定文件 @return String 返回类型 @throws
     */
    public static String doPost(String url, Map<String, Object> params) {
        Assert.hasText(url, "url信息不能为空！");
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
        try {
            HttpPost httpPost = new HttpPost(url);
            if (!CollectionUtils.isEmpty(params)) {
                httpPost.setEntity(new UrlEncodedFormEntity(convertParams(params), Consts.UTF_8));
            }
            // 执行http请求
            return getResponseEntityResult(httpClient.execute(httpPost));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * @param url
     * @param headVal 可为空
     * @param jsonStr
     * @return String
     * @className: (参数为json字符串的请求)
     * @date 2018/05/30 15:53:45
     */
    public static String doPostJson(String url, Map<String, String> headVal, String jsonStr) {
        Assert.hasText(url, "url信息不能为空！");
        // 创建Httpclient对象  
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
        try {
            // 创建Http Post请求  
            HttpPost httpPost = new HttpPost(url);
            if (!CollectionUtils.isEmpty(headVal))
                httpPost.setHeaders(createHeadersByMap(headVal));
            if (StringUtils.isNotEmpty(jsonStr)) {
                StringEntity entity = new StringEntity(jsonStr, "utf-8");
                httpPost.setEntity(entity);
                entity.setContentType("application/json");
            }
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            return getResponseEntityResult(httpClient.execute(httpPost));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param map
     * @return files
     * @className: (这里用一句话描述这个类的作用或者名称)
     * @date 2018/05/30 15:56:12
     */
    public static String sendUploadCommon(String url, Map<String, Object> map, Map<String, File> files)
            throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
        String body = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            // httpPost.setConfig(requestConfig);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            /**
             * 添加文件
             */
            if (files != null) {
                for (Entry<String, File> entry : files.entrySet()) {
                    multipartEntityBuilder.addPart(entry.getKey(), new FileBody(entry.getValue()));
                }
            }
            //httpPost.setHeader("partner-identify", StringUtils.isEmpty(getXydHeather()) ? null : getXydHeather());
            /**
             * 装填参数
             */
            if (map != null) {
                for (Entry<String, Object> entry : map.entrySet()) {
                    multipartEntityBuilder.addPart(entry.getKey(), new StringBody((String) entry.getValue(), ContentType.TEXT_PLAIN));
                }
            }
            httpPost.setEntity(multipartEntityBuilder.build());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                // 获取结果实体
                HttpEntity entity = response.getEntity();
                if (entity != null && response.getStatusLine().getStatusCode() == 200) {
                    // 按指定编码转换结果实体为String类型
                    body = EntityUtils.toString(entity, Consts.UTF_8);
                }
                EntityUtils.consume(entity);
            } finally {
                // 释放链接
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpclient.close();
        }
        return body;

    }

    /**
     * @param url
     * @param params
     * @return String
     * @className: (发起get请求)
     * @date 2018/05/30 15:57:36
     */
    public static String doGet(String url, Map<String, Object> params) {
        Assert.hasText(url, "url不能为空！");
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
        try {
            url += (StringUtils.contains(url, "?") ? "&" : "?")
                    + EntityUtils.toString(new UrlEncodedFormEntity(convertParams(params), Consts.UTF_8));
            return getResponseEntityResult(httpClient.execute(new HttpGet(url)));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param ip ip地址
     * @return String    返回类型
     * @throws
     * @Title: getRegion
     * @Description: (根据ip地址获取所在地区)
     */
    public static String getRegion(String ip) {
        if (!StringUtils.isEmpty(ip) && (ip.indexOf("192.168") >= 0 || ip.indexOf("127.0.0.1") >= 0 || ip.indexOf("0:0:0:0:0:0:0:1") >= 0)) {
            return "地区未知,可能是本地测试";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("ip", ip);
        // 淘宝根据ip获取归属地
        String region = doGet(queryRegionByTaobaoUrl, params);
        JSONObject jsonObject = JSONObject.parseObject(region);
        Integer code = jsonObject.getInteger("code");
        if (code == null) {
            logger.info("淘宝获取根据ip获取归属地失败！");
            return sina(params);
        }
        if (code != 0) {
            logger.info("淘宝获取根据ip获取归属地失败！");
            return sina(params);
        }
        JSONObject data = jsonObject.getJSONObject("data");
        if (data == null) {
            logger.info("淘宝获取根据ip获取归属地失败！");
            return sina(params);
        }
        String regionInfo = data.getString("region");
        if (!StringUtils.isEmpty(regionInfo)) {
            try {
                return new String(regionInfo.getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return regionInfo;
    }

    /**
     * @param headersMap
     * @return Header[]
     * @className: (创建好自定义头部信息)
     * @date 2018/06/05 20:11:19
     */
    public static Header[] createHeadersByMap(Map<String, String> headersMap) {
        List<Header> headers = new ArrayList<>();
        headersMap.forEach((k, v) -> headers.add(new BasicHeader(k, v)));
        if (!CollectionUtils.isEmpty(headers)) {
            Header[] _headers = new Header[headers.size()];
            return headers.toArray(_headers);
        }
        return null;
    }

    /**
     * 创建Header(模拟浏览器的header值)
     */
    public static Header[] createHeaders(Header session) {

        List<Header> headers = new ArrayList<>();

        if (session != null) {
            headers.add(session);
        }

        Header header = null;
        header = new BasicHeader(HttpHeaders.CONTENT_TYPE,
                "application/x-www-form-urlencoded");
        headers.add(header);
        header = new BasicHeader("DNT", "1");
        headers.add(header);

        header = new BasicHeader("Pragma", "no-cache");
        headers.add(header);

        header = new BasicHeader(HttpHeaders.USER_AGENT,
                "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
        headers.add(header);

        header = new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN");
        headers.add(header);

        header = new BasicHeader(HttpHeaders.ACCEPT,
                "text/html, application/xhtml+xml, */*");
        headers.add(header);

        header = new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
        headers.add(header);

        header = new BasicHeader(HttpHeaders.CONNECTION, "Keep-Alive");
        headers.add(header);

        Header[] _headers = new Header[headers.size()];
        headers.<Header>toArray(_headers);

        return _headers;
    }

    /**
     * @param params
     * @return 返回类型
     * @className: (根据新浪api获取)
     * @date 2018/05/30 16:00:54
     */
    public static String sina(Map<String, Object> params) {
        String region = doGet(queryRegionBySinaUrl, params);
        if (!StringUtils.isEmpty(region))
            region = region.trim();//返回的数据带有大量空格
        return region;
    }


    /**
     * 请求工具类map参数公共转换
     *
     * @param params
     * @return
     */
    public static List<NameValuePair> convertParams(Map<String, Object> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (params != null) {
            params.forEach((k, v) -> {
                String value = ConvertUtils.convert(v);
                if (StringUtils.isNotBlank(k)) {
                    nameValuePairs.add(new BasicNameValuePair(k, value));
                }
            });
        }
        return nameValuePairs;
    }

    public static String getResponseEntityResult(CloseableHttpResponse response) {
        try {
            try {
                if (response != null && response.getStatusLine().getStatusCode() == 200 && response.getEntity() != null) {
                    return EntityUtils.toString(response.getEntity(), Consts.UTF_8);
                }
            } finally {
                EntityUtils.consume(response.getEntity());
                response.close();
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 检查ip或域名是否异常
     *
     * @param host
     * @return
     */
    public static boolean isHostReachable(String host) {
        try {
            return InetAddress.getByName(host).isReachable(6000);
        } catch (UnknownHostException e) {
            logger.error(">>>>>>>>>>>>IP错误，无法访问");
        } catch (IOException e) {
            logger.error(">>>>>>>>>>>>通信异常" + e.getMessage());
        }
        return false;
    }

    public static String getAddressIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Real-IP");
        if (ipAddress != null && ipAddress.length() > 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
            return ipAddress;
        }
        ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                try {
                    ipAddress = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        // "***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static void main(String[] args) {
        InetSocketAddress isa = new InetSocketAddress("118.190.155.179", 9300);
        Socket s = new Socket();
        try {
            s.connect(isa, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
