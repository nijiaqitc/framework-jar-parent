package com.njq.common.util.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.njq.common.util.date.DateUtil;

import sun.misc.BASE64Decoder;

public class UpPicUtil {
    private static Logger logger = LoggerFactory.getLogger(UpPicUtil.class);


    /**
     * 上传二进制图片
     * @param item
     * @param filePlace
     * @return
     */
    public static String upBlobPic(FileItem item,String filePlace){
        try {
            String uuid;
            String tempFilePlace=filePlace;
            if (!item.isFormField()) {
                // 获得文件名
                String fileName = item.getName();
                // 该方法在某些平台(操作系统),会返回路径+文件名/Users/njq
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                uuid=UUID.randomUUID().toString();
                fileName = DateUtil.toDateString6(new Date())+uuid.substring(uuid.length()-4,uuid.length())+"."+fileName.split("\\.")[1];
                File fileFolder=new File(tempFilePlace);
                //创建目录
                if(!fileFolder.exists()){
                    fileFolder.mkdirs();                        
                }
                tempFilePlace+="/"+fileName;
                File file = new File(tempFilePlace);
                if(!file.exists()) {
                    item.write(file);
                    return fileName;
                }
            }
        } catch (Exception e) {
            logger.error("上传图片失败",e);
        }
        return null;
    }
    
    /**
     * 上传base64位图片
     * @param base64Data
     * @return
     */
    public static String upBase64Pic(String base64Data, String savePath){
        BASE64Decoder decoder = new BASE64Decoder();
        String fileName,uuid;
        try{
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64Data.split("base64,")[1]);
            for(int i=0;i<b.length;++i){
                //调整异常数据
                if(b[i]<0){
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            File dir=new File(savePath);
            if(!dir.exists()){
             dir.mkdirs();
            }
            uuid=UUID.randomUUID().toString();
            fileName = DateUtil.toDateString6(new Date())+uuid.substring(uuid.length()-4,uuid.length())+".jpg";
            String realPath = savePath+"/"+fileName;
            OutputStream out = new FileOutputStream(realPath);    
            out.write(b);
            out.flush();
            out.close();
            return fileName;
        }catch (Exception e){
            return "";
        }
    }
    
    /**
     * 上传网络图片
     * @param urlString 网址
     * @param savePath 保存地址
     * @throws Exception
     */
    public static String upIntenetPic(String urlString, String savePath) {
        InputStream is=null;
        OutputStream os=null;
        String uuid=UUID.randomUUID().toString();
        String fileName = DateUtil.toDateString6(new Date())+uuid.substring(uuid.length()-4,uuid.length())+".jpg";
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            // 设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
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
            logger.error("上传网络图片失败，网络地址："+urlString,e);
        }finally{
            // 完毕，关闭所有链接
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }
     
}
