package com.njq.common.util.other;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class IpUtil{
    /**
	 * 获取 ip地址
	 */
	@SuppressWarnings("rawtypes")
	public static String getIpAddr(HttpServletRequest request) {
		String ip = null;
		Enumeration enu = request.getHeaderNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			if(name.equalsIgnoreCase("X-Real_IP")){
				ip = request.getHeader(name);
			}else if (name.equalsIgnoreCase("X-Forwarded-For")) {
				ip = request.getHeader(name);
			} else if (name.equalsIgnoreCase("Proxy-Client-IP")) {
				ip = request.getHeader(name);
			} else if (name.equalsIgnoreCase("WL-Proxy-Client-IP")) {
				ip = request.getHeader(name);
			}
			if ((ip != null) && (ip.length() != 0))
				break;
		}

		if ((ip == null) || (ip.length() == 0))
			ip = request.getRemoteAddr();

		return ip;
	}
}    
