package com.njq.common.util.grab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class UrlChangeUtil {
    private static final Logger logger = LoggerFactory.getLogger(UrlChangeUtil.class);

    public static void downLoad(String urlString, String fileName, String shortName) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if (con.getResponseCode() == 301) {
            con.disconnect();
            url = new URL(con.getHeaderField("Location"));
            con = (HttpURLConnection) url.openConnection();
        }
        if(con.getResponseCode() != 200){
            logger.error("访问失败："+urlString+" 响应code："+con.getResponseCode());
            throw new RuntimeException("响应失败！ "+con.getResponseCode());
        }
        if (shortName != null) {
            setConnection(con, shortName);
        }
        InputStream is = null;
        GZIPInputStream gis = null;
        OutputStream os = null;
        try {
            is = con.getInputStream();
            String code = con.getHeaderField(SendConstants.CONTENT_ENCODING_NAME);
            if ((null != code) && code.equals(SendConstants.CONTENT_ENCODING_VALUE)) {
                gis = new GZIPInputStream(is);
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 输出的文件流
                os = new FileOutputStream(fileName);
                // 开始读取
                while ((len = gis.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
            } else {
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 输出的文件流
                os = new FileOutputStream(fileName);
                // 开始读取
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
            }
        } catch (Exception e) {
            logger.error("转换图片出错" + url, e);
            throw e;
        } finally {
            try {
                if (gis != null) {
                    gis.close();
                }
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
                if (con != null) {
                    con.disconnect();
                }
            } catch (Exception e1) {
                logger.error("关闭流出错", e1);
            }
        }
    }


    public static void setConnection(HttpURLConnection con, String shortName) {
        con.setConnectTimeout(30000);
        con.setReadTimeout(30000);
        con.setRequestProperty(SendConstants.USER_AGENT_NAME, SendConstants.USER_AGENT_VALUE);
        con.setRequestProperty(SendConstants.ACCEPT_NAME, SendConstants.ACCEPT_VALUE);
        con.setRequestProperty(SendConstants.ACCEPT_LANGUAGE_NAME, SendConstants.ACCEPT_LANGUAGE_VALUE);
        // 注意编码，gzip可能会乱码
        con.setRequestProperty(SendConstants.ACCEPT_ENCODING_NAME, SendConstants.ACCEPT_ENCODING_VALUE);
        con.setRequestProperty(SendConstants.CONNECTION_NAME, SendConstants.CONNECTION_VALUE);
        con.setRequestProperty(SendConstants.UPGRADE_INSECURE_REQUESTS_NAME,
                SendConstants.UPGRADE_INSECURE_REQUESTS_VALUE);
        con.setRequestProperty(SendConstants.CACHE_CONTROL_NAME, SendConstants.CACHE_CONTROL_VALUE);
        con.setRequestProperty(SendConstants.COOKIE_NAME, HtmlGrabUtil.build(shortName).getCookieStr());
    }
}
