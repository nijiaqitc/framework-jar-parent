package com.njq.common.util.other;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtil {

	private static Properties props = new Properties();
	private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

//	static {
//		try {
//			
//			props.load(PropertyUtil.class.getResourceAsStream("/paramForTest.properties"));
//		} catch (IOException e) {
//			logger.error("加载配置文件异常", e);
//		}
//	}

	public static void init(ServletContext sc){
		try {
			if(sc.getInitParameter("envConfig")!=null){
				props.load(PropertyUtil.class.getResourceAsStream("/paramForProduct.properties"));
			}else{
				props.load(PropertyUtil.class.getResourceAsStream("/paramForTest.properties"));
			}
		} catch (IOException e) {
			logger.error("加载配置文件异常", e);
		}
	}
	
	public static String get(String key) {
		return props.getProperty(key);
	}
	
	
}
