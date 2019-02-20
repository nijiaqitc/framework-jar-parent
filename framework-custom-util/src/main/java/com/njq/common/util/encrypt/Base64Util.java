package com.njq.common.util.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Base64Util {
    private static final Logger logger = LoggerFactory.getLogger(Base64Util.class);

    /**
     * base64位加密
     *
     * @param str
     * @return
     */
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("字符串编码转换出错", e);
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    /**
     * base64位解密
     *
     * @param s
     * @return
     */
    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                logger.error("base64解密失败", e);
            }
        }
        return result;
    }


    /**
     * 图片转化成base64字符串
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     */
    public static String GetImageStr(String imgUrl) {
        String imgFile = imgUrl;
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            logger.error("图片转换base出错", e);
        } finally {
            try {
                in.close();
            } catch (Exception e1) {
                logger.error("关闭流出错", e1);
            }
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    /**
     * base64字符串转化成图片并输出到本地
     *
     * @param imgStr base64 去掉前缀
     * @param name   图片名称
     * @return 2016-3-27
     * author njq
     */
    public static String GenerateImage(String imgStr, String name, String place) {
        /*
         * 对字节数组字符串进行Base64解码并生成图片
         */
        if (imgStr == null) {
            return "";
        }
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                /*
                 * 调整异常数据
                 */
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            Date timeCur = new Date();
            SimpleDateFormat fmtYY = new SimpleDateFormat("yyyy");
            SimpleDateFormat fmtMM = new SimpleDateFormat("MM");
            SimpleDateFormat fmtDD = new SimpleDateFormat("dd");
            String strYY = fmtYY.format(timeCur);
            String strMM = fmtMM.format(timeCur);
            String strDD = fmtDD.format(timeCur);
            String realPath = place + "/docpic/" + strYY + strMM + strDD;
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = "/docpic/" + strYY + strMM + strDD + "/" + name + ".jpg";
            //新生成的图片
            String imgFilePath = realPath + "/" + name + ".jpg";
            out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            return fileName;
        } catch (Exception e) {
            return "";
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e1) {
                    logger.error("关闭流出错", e1);
                }
            }
        }
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     */
    public static String GenerateImage(String imgStr, String name, String place, Map<String, Object> map) {
        /*
         * 图像数据为空
         */
        if (imgStr == null) {
            return "";
        }
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                /*
                 * 调整异常数据
                 */
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            File dir = new File(place);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //新生成的图片
            String imgFilePath = place + "/" + name + ".jpg";
            out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return name + ".jpg";
        } catch (Exception e) {
            return "";
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e1) {
                    logger.error("关闭流出错", e1);
                }
            }
        }
    }
}

