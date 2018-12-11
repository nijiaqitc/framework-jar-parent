/**
 * 一些涉及到业务的工具类
 */
package com.njq.common.util.rely;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.njq.common.util.other.PropertyUtil;

public class APIUtil {
	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responeLocal = new ThreadLocal<HttpServletResponse>();

	public static void initValue(HttpServletRequest request, HttpServletResponse response) {
		requestLocal.set(request);
		responeLocal.set(response);
	}

	public static void remove() {
		requestLocal.remove();
		responeLocal.remove();
	}

	/**
	 * 判断请求的域名，查询对应的数据 如：aaa域名只能查询A类的数据，bbb域名只能查询B类的数据
	 * 
	 * @param cc
	 */
	public static boolean getUrl() {
		// 获取当前的请求域名
		String serverName = getCurrentRequest().getServerName();
		if (serverName.equals(PropertyUtil.get("access.url"))) {
			return true;
		}
		return false;
	}

	/**
	 * 判断url请求的类型
	 * 
	 * @return
	 */
	public static boolean checkRequestUri() {
		String serverName = getCurrentRequest().getServerName();
		if (serverName.equals(PropertyUtil.get("access.url"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取当前线程对应的request
	 * 
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getCurrentRequest() {
		return requestLocal.get();
	}

	/**
	 * 获取当前session
	 * 
	 * @return
	 */
	public static HttpSession getCurrentSession() {
		return requestLocal.get().getSession();
	}

	/**
	 * 获取当前线程对应的respone
	 * 
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getCurrentResponse() {
		return responeLocal.get();
	}

	/**
	 * 获取当前线程对应的request的cookie
	 * 
	 * @return HttpServletRequest
	 */
	public static Cookie[] getCurrentCookies() {
		return getCurrentRequest().getCookies();
	}
}
