package com.njq.common.util.grab;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;

public class UrlChangeUtil {
    public static String changeSrcUrl(String prefix, String src, String shortName, String savePlace) {
        String fileName = System.currentTimeMillis() + "";
        if (!src.startsWith(SendConstants.HTTP_PREFIX)) {
            String[] img = src.split("\\?")[0].split("\\/");
            String[] imgName = img[img.length - 1].split("\\.");
            fileName += "." + imgName[1];
            src = prefix + src;
        }
        Date timeCur = new Date();
        SimpleDateFormat fmtYY = new SimpleDateFormat("yyyy");
        SimpleDateFormat fmtMM = new SimpleDateFormat("MM");
        SimpleDateFormat fmtDD = new SimpleDateFormat("dd");
        String strYY = fmtYY.format(timeCur);
        String strMM = fmtMM.format(timeCur);
        String strDD = fmtDD.format(timeCur);
        String url = "/" + shortName + "/" + strYY + strMM + strDD + "/" + fileName;
        try {
            downLoad(src, savePlace + url, shortName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static void downLoad(String urlString, String fileName, String shortName) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        if (shortName != null) {
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
            // con.setRequestProperty("Content-Type", "application/pdf");
        }
        // 输入流
        InputStream is = con.getInputStream();
        String code = con.getHeaderField(SendConstants.CONTENT_ENCODING_NAME);
        if ((null != code) && code.equals(SendConstants.CONTENT_ENCODING_VALUE)) {
            GZIPInputStream gis = new GZIPInputStream(is);
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            OutputStream os = new FileOutputStream(fileName);
            // 开始读取
            while ((len = gis.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            gis.close();
            os.close();
            is.close();
        } else {
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            OutputStream os = new FileOutputStream(fileName);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        }
    }
}
