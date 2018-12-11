package com.njq.common.util.grab;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

public class UrlChangeUtil {
	private static final String savePlace = "D:\\worksts\\ppcong\\target\\classes\\webapp\\static\\";

	public static String changeHrefUrl(String prefix, String src) {
		if (!src.startsWith(SendConstants.HTTP_PREFIX)) {
			src = prefix + src;
		}
		return src;
	}

	public static String changeSrcUrl(String prefix, String src, String shortName) {
		String fileName = System.currentTimeMillis() + "";
		if (!src.startsWith(SendConstants.HTTP_PREFIX)) {
			String[] img = src.split("\\?")[0].split("\\/");
			String[] imgName = img[img.length - 1].split("\\.");
			fileName += "."+imgName[1];
			src = prefix + src;
		}
		try {
			downLoad(src, savePlace + fileName, shortName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/" + fileName;
	}

	public static void downLoad(String urlString, String fileName, String shortName) throws Exception {
		URL url = new URL(urlString);// 构造URL
		URLConnection con = url.openConnection();// 打开连接
		if (shortName != null) {
			con.setConnectTimeout(30000);
			con.setReadTimeout(30000);
			con.setRequestProperty(SendConstants.USER_AGENT_NAME, SendConstants.USER_AGENT_VALUE);
			con.setRequestProperty(SendConstants.ACCEPT_NAME, SendConstants.ACCEPT_VALUE);
			con.setRequestProperty(SendConstants.ACCEPT_LANGUAGE_NAME, SendConstants.ACCEPT_LANGUAGE_VALUE);
			con.setRequestProperty(SendConstants.ACCEPT_ENCODING_NAME, SendConstants.ACCEPT_ENCODING_VALUE);// 注意编码，gzip可能会乱码
			con.setRequestProperty(SendConstants.CONNECTION_NAME, SendConstants.CONNECTION_VALUE);
			con.setRequestProperty(SendConstants.UPGRADE_INSECURE_REQUESTS_NAME,
					SendConstants.UPGRADE_INSECURE_REQUESTS_VALUE);
			con.setRequestProperty(SendConstants.CACHE_CONTROL_NAME, SendConstants.CACHE_CONTROL_VALUE);
			con.setRequestProperty(SendConstants.COOKIE_NAME, HtmlGrabUtil.build(shortName).getCookieStr());
			// con.setRequestProperty("Content-Type", "application/pdf");
		}
		InputStream is = con.getInputStream();// 输入流
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
