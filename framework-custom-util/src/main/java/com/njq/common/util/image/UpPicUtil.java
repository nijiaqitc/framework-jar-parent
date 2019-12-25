package com.njq.common.util.image;

import com.njq.common.util.grab.UrlChangeUtil;
import com.njq.common.util.string.IdGen;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpPicUtil {
    private static Logger logger = LoggerFactory.getLogger(UpPicUtil.class);

    public static void upByte(byte[] b, String realPath) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(realPath);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            logger.error("上传图片出错", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e1) {
                logger.error("关闭流出错", e1);
            }
        }
    }


    /**
     * 上传二进制图片
     *
     * @param item
     * @param filePlace
     * @return
     */
    public static String upBlobPic(FileItem item, String filePlace) {
        try {
            String tempFilePlace = filePlace;
            if (!item.isFormField()) {
                // 获得文件名
                String fileName = item.getName();
                // 该方法在某些平台(操作系统),会返回路径+文件名/Users/njq
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                fileName = IdGen.get() + "." + fileName.split("\\.")[1];
                File fileFolder = new File(tempFilePlace);
                //创建目录
                if (!fileFolder.exists()) {
                    fileFolder.mkdirs();
                }
                tempFilePlace += "/" + fileName;
                File file = new File(tempFilePlace);
                if (!file.exists()) {
                    item.write(file);
                    return fileName;
                }
            }
        } catch (Exception e) {
            logger.error("上传图片失败", e);
        }
        return null;
    }

    /**
     * 上传base64位图片
     *
     * @param base64Data
     * @return
     */
    public static String upBase64Pic(String base64Data, String savePath) {
        BASE64Decoder decoder = new BASE64Decoder();
        String fileName;
        OutputStream out = null;
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64Data.split("base64,")[1]);
            for (int i = 0; i < b.length; ++i) {
                //调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            File dir = new File(savePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            fileName = IdGen.get() + ".jpg";
            String realPath = savePath + "/" + fileName;
            out = new FileOutputStream(realPath);
            out.write(b);
            out.flush();
            return fileName;
        } catch (Exception e) {
            logger.error("上传图片出错", e);
            return "";
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e1) {
                logger.error("关闭流出错", e1);
            }
        }
    }

    /**
     * 上传网络图片
     *
     * @param urlString 网址
     * @param savePath  保存地址
     * @throws Exception
     */
    public static String upIntenetPic(String urlString, String savePath) {
        InputStream is = null;
        OutputStream os = null;
        HttpURLConnection con = null;
        String fileName = IdGen.get() + ".jpg";
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            con = (HttpURLConnection) url.openConnection();
            // 设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
            UrlChangeUtil.setConnection(con, null);
            UrlChangeUtil.setRandomIp(con);
            // 输入流
            is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf = new File(savePath);
            if (!sf.exists()) {
                sf.mkdirs();
            }
            os = new FileOutputStream(sf.getPath() + "/" + fileName);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (Exception e) {
            logger.error("上传网络图片失败，网络地址：" + urlString, e);
        } finally {
            // 完毕，关闭所有链接
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
                if (con != null) {
                    con.disconnect();
                }
            } catch (IOException e) {
                logger.error("关闭流出错", e);
            }
        }
        return fileName;
    }

}
