package com.njq.common.util.grab;

import com.njq.common.util.string.StringUtil2;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlGrabUtil {
    private static final Logger logger = LoggerFactory.getLogger(HtmlGrabUtil.class);
    public static Map<String, HtmlGrabUtil> customMap = new HashMap<>();
    private CookieStore store;
    private RequestConfig requestConfig;
    private String moIp;
    private Boolean randomIpFlag = true;

    public HtmlGrabUtil() {
        requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10 * 1000).setConnectTimeout(10 * 1000)
                .setSocketTimeout(10 * 1000).build();
    }

    public CookieStore getCookieStore() {
        return store;
    }

    public String getCookieStr() {
        String str = "";
        if (this.store != null) {
            List<Cookie> cookies = store.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                str += cookies.get(i).getName() + "=" + cookies.get(i).getValue() + ";";
            }
        }
        return str;
    }

    public static HtmlGrabUtil build(String key) {
        if (customMap.get(key) == null) {
            synchronized (customMap) {
                if (customMap.get(key) == null) {
                    customMap.put(key, new HtmlGrabUtil());
                }
            }
        }
        return customMap.get(key);
    }

    /**
     * 构建多例的对象
     *
     * @param key
     * @return
     */
    public static HtmlGrabUtil buildProto(String key) {
        return new HtmlGrabUtil();
    }

    public HtmlGrabUtil setCookie(String key, String value, String domain) {
        store = new BasicCookieStore();
        if (StringUtil2.IsNotEmpty(key)) {
            BasicClientCookie bcookie = new BasicClientCookie(key, value);
            if (StringUtil2.IsNotEmpty(domain)) {
                //domain 必须用.开始,  domain指可以访问该cookie的域名
                bcookie.setDomain(domain);
            }
            bcookie.setPath("/");
            store.addCookie(bcookie);
        }
        return this;
    }

    public HtmlGrabUtil setFlag(boolean flag) {
        this.randomIpFlag = flag;
        return this;
    }

    /**
     * 默认随机产生ip
     */
    public HtmlGrabUtil randomSendIp() {
        moIp = GenerateRandomIpUtil.getRandomIp();
        return this;
    }

    public HtmlGrabUtil setSendIp(String ip) {
        moIp = ip;
        return this;
    }

    public String getSendIp() {
        return moIp;
    }

    public void login(String url, String type, Map<String, String> paramsMap) {
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        paramsMap.entrySet().forEach(n -> {
            formParams.add(new BasicNameValuePair(n.getKey(), n.getValue()));
        });
        login(url, type, formParams);
    }

    public void login(String url, String type, List<NameValuePair> formParams) {
        try {
            store = new BasicCookieStore();
            sendPostFromUrl(url, formParams);
        } catch (Exception e) {
            logger.error("登录信息出错" + url, e);
        }
    }

    public String sendPostFromUrl(String url, List<NameValuePair> formParams) {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
        HttpResponse response = null;
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, SendConstants.ENCODE);
            HttpPost postHttp = new HttpPost(url);
            postHttp.setConfig(requestConfig);
            // 设置HTTP Header
            setPostHeader(postHttp, null);
            logger.info("grab开始发送post请求----ip:" + moIp + "----发送的url：---" + url+" 参数："+formParams);
            postHttp.setEntity(formEntity);
            response = httpClient.execute(postHttp);
            return EntityUtils.toString(response.getEntity(), SendConstants.ENCODE);
        } catch (Exception e) {
            logger.error("发送post信息出错" + url, e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    ((CloseableHttpResponse) response).close();
                }
            } catch (Exception e) {
                logger.error("关闭流出错", e);
            }
        }
        return null;
    }

    public String sendPostFromUrlJson(String url, String jsonStr) {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
        HttpResponse response = null;
        try {
            StringEntity formEntity = new StringEntity(jsonStr, SendConstants.ENCODE);
            HttpPost postHttp = new HttpPost(url);
            postHttp.setConfig(requestConfig);
            // 设置HTTP Header
            setPostHeader(postHttp, "json");
            logger.info("grab开始发送post请求----ip:" + moIp + "----发送的url：---" + url +"参数："+jsonStr);
            postHttp.setEntity(formEntity);
            response = httpClient.execute(postHttp);
            return EntityUtils.toString(response.getEntity(), SendConstants.ENCODE);
        } catch (Exception e) {
            logger.error("发送post信息出错" + url, e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    ((CloseableHttpResponse) response).close();
                }
            } catch (Exception e) {
                logger.error("关闭流出错", e);
            }
        }
        return null;
    }


    public Document getDoc(String url) {
        String doc = getContext(url);
        if (doc == null) {
            return null;
        } else {
            return Jsoup.parse(doc);
        }
    }

    public Document getDoc(String url, String key, String value, String domain) {
        setCookie(key, value, domain);
        String doc = getContext(url);
        if (doc == null) {
            return null;
        } else {
            return Jsoup.parse(doc);
        }
    }

    public String getContext(String url) {
        try {
            return sendGetFromUrl(url);
        } catch (Exception e) {
            logger.error("获取信息出错" + url, e);
        }
        return "";
    }

    public String sendGetFromUrl(String url) {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
        HttpResponse response = null;
        try {
            HttpGet getHttp = new HttpGet(url);
            getHttp.setConfig(requestConfig);
            // 设置HTTP Header
            setGetHeader(getHttp);
            logger.info("grab开始发送get请求----ip:" + moIp + "----发送的url：---" + url);
            response = httpClient.execute(getHttp);
            String charset = response.getEntity().getContentType().getValue().toUpperCase();
            if (charset.contains(SendConstants.ENCODE)) {
                return EntityUtils.toString(response.getEntity(), SendConstants.ENCODE);
            } else if (charset.contains(SendConstants.GB)) {
                return EntityUtils.toString(response.getEntity(), SendConstants.GB);
            } else if (charset.contains(SendConstants.ISO)) {
                return EntityUtils.toString(response.getEntity(), SendConstants.ISO);
            } else {
                return EntityUtils.toString(response.getEntity(), SendConstants.GBK);
            }
        } catch (Exception e) {
            logger.error("发送get信息出错" + url, e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    ((CloseableHttpResponse) response).close();
                }
            } catch (Exception e) {
                logger.error("关闭流出错", e);
            }
        }
        return null;
    }


    private void setPostHeader(HttpPost postHttp, String postType) {
        setUserAgent(postHttp);
        if ("json".equals(postType)) {
            postHttp.addHeader(SendConstants.CONTENT_TYPE_NAME, "application/json; charset=UTF-8");
        } else {
            postHttp.addHeader(SendConstants.CONTENT_TYPE_NAME, SendConstants.CONTENT_TYPE_VALUE);
        }
        if (randomIpFlag) {
            randomSendIp();
        }
        setIp(postHttp);
    }

    private void setGetHeader(HttpGet getHttp) {
        setUserAgent(getHttp);
        getHttp.addHeader(SendConstants.CONTENT_TYPE_NAME, SendConstants.CONTENT_TYPE_VALUE);
        if (randomIpFlag) {
            randomSendIp();
        }
        setIp(getHttp);
    }

    private void setUserAgent(AbstractHttpMessage message) {
        if (this.getUserAgent() != null) {
            message.setHeader(SendConstants.USER_AGENT_NAME, this.getUserAgent());
        } else {
            message.setHeader(SendConstants.USER_AGENT_NAME, SendConstants.USER_AGENT_VALUE);
        }
    }

    private void setIp(AbstractHttpMessage message) {
        message.addHeader(SendConstants.HEAD_IP_1, moIp);
        message.addHeader(SendConstants.HEAD_IP_2, moIp);
        message.addHeader(SendConstants.HEAD_IP_3, moIp);
        message.addHeader(SendConstants.HEAD_IP_4, moIp);
        message.addHeader(SendConstants.HEAD_IP_5, moIp);
    }

    private String userAgent;

    public String getUserAgent() {
        return userAgent;
    }

    public HtmlGrabUtil setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

}
