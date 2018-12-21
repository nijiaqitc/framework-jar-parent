package com.njq.common.util.other;

import com.njq.common.util.rely.APIUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类 统一登录 cookie处理放这
 * 使用前需要配置ResponseInterceptor
 */
public class CookieUtil {

    /**
     * 应用根目录
     */
    public static final String ROOT_PATH = "/";

    private static final int DEAD_0 = 0;

    /**
     * 添加5分钟的cookie
     *
     * @param key
     * @param value 2016年7月7日
     *              author njq
     */
    public static void addCookie(String key, String value) {
        Cookie c = new Cookie(key, value);
        c.setMaxAge(CookieExpire.FIVE.getExpireTime());
        c.setPath(ROOT_PATH);
        APIUtil.getCurrentResponse().addCookie(c);
    }

    /**
     * 自定义增加cookie值
     *
     * @param cookieName
     * @param cookieValue
     * @param cookieExpire
     */
    public static void addCookie(String cookieName, String cookieValue, CookieExpire cookieExpire) {
        Cookie c = new Cookie(cookieName, cookieValue);
        c.setMaxAge(cookieExpire.getExpireTime());
        c.setPath(ROOT_PATH);
        APIUtil.getCurrentResponse().addCookie(c);
    }

    /**
     * 根据cookieName 获取cookieValue
     *
     * @param cookieName
     * @return String
     */
    public static String getCookieVaule(String cookieName) {
        if (cookieName == "") {
            return null;
        }
        Cookie[] cookies = APIUtil.getCurrentRequest().getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 客户端删除cookie 根据cookieName删除
     *
     * @param cookieName
     */
    public static void removeCookieByName(String cookieName) {
        Cookie c = new Cookie(cookieName, null);
        c.setMaxAge(DEAD_0);
        c.setPath(ROOT_PATH);
        APIUtil.getCurrentResponse().addCookie(c);
    }


    /**
     * 自定义增加cookie值
     *
     * @param cookieName
     * @param cookieValue
     * @param cookieExpire
     */
    public static void addCookie(String cookieName, String cookieValue, CookieExpire cookieExpire, HttpServletResponse resp) {
        Cookie c = new Cookie(cookieName, cookieValue);
        c.setMaxAge(cookieExpire.getExpireTime());
        c.setPath(ROOT_PATH);
        resp.addCookie(c);
    }

    /**
     * 根据cookieName 获取cookieValue
     *
     * @param cookieName
     * @return String
     */
    public static String getCookieVaule(String cookieName, HttpServletRequest req) {
        if (cookieName == "") {
            return null;
        }
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 客户端删除cookie 根据cookieName删除
     *
     * @param userId
     */
    public static void removeCookieByName(String cookieName, HttpServletResponse resp) {
        Cookie c = new Cookie(cookieName, null);
        c.setMaxAge(DEAD_0);
        c.setPath(ROOT_PATH);
        resp.addCookie(c);
    }

}
