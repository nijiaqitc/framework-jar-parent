package com.njq.common.util.grab;

import com.njq.common.util.string.IdGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;

public class UrlChangeUtil {
    private static final Logger logger = LoggerFactory.getLogger(UrlChangeUtil.class);

    public static String changeSrcUrl(String prefix, String src, String shortName, String savePlace) {
        String fileName = String.valueOf(IdGen.get().nextId());
        String[] img = src.split("\\?")[0].split("\\/");
        String[] imgName = img[img.length - 1].split("\\.");
        if (imgName.length > 1) {
            fileName += "." + imgName[1];
        } else {
            fileName += ".png";
        }
        if (!src.startsWith(SendConstants.HTTP_PREFIX)) {
            src = prefix + src;
        }

        String url = getSrc(shortName, savePlace) + "/" + fileName;
        try {
            downLoad(src, savePlace + url, shortName);
        } catch (Exception e) {
            logger.error("下载出错", e);
            return src;
        }
        return url;
    }

    private static String getSrc(String shortName, String savePlace) {
        Date timeCur = new Date();
        SimpleDateFormat fmtYY = new SimpleDateFormat("yyyy");
        SimpleDateFormat fmtMM = new SimpleDateFormat("MM");
        SimpleDateFormat fmtDD = new SimpleDateFormat("dd");
        String strYY = fmtYY.format(timeCur);
        String strMM = fmtMM.format(timeCur);
        String strDD = fmtDD.format(timeCur);
        String url = "/" + shortName + "/" + strYY + strMM + strDD;
        File dir = new File(savePlace + url);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return url;
    }

    public static String changeFileUrl(String prefix, String src, String shortName, String savePlace) {
        if (!src.startsWith(SendConstants.HTTP_PREFIX)) {
            String[] img = src.split("\\?")[0].split("\\/");
            String url = getSrc(shortName, savePlace);
            try {
                String saveRealPlace = savePlace + url + "/" + img[img.length - 1];
                downLoad(prefix + src, URLDecoder.decode(saveRealPlace, "UTF-8"), shortName);
                return url + "/downLoadFile?file=" + img[img.length - 1];
            } catch (Exception e) {
                logger.error("下载出错", e);
                return src;
            }
        }
        return null;
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
        }
        // 输入流
        InputStream is = con.getInputStream();
        String code = con.getHeaderField(SendConstants.CONTENT_ENCODING_NAME);
        GZIPInputStream gis = null;
        OutputStream os = null;
        try {
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
            logger.error("转换图片出错", e);
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
            } catch (Exception e1) {
                logger.error("关闭流出错", e1);
            }
        }
    }
}
