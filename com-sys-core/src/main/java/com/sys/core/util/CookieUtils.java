package com.sys.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对Cookie进行操作
 * Created by Administrator on 2018/6/14/014.
 */
public class CookieUtils {

    /**
     * 获取cookie中的sessionId
     * @param request request请求域名
     * @return sessionId
     */
    public static String getValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String sessionId = "";
        if(cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("JSESSIONID")){
                    sessionId = cookie.getValue();
                }
            }
        }
        return sessionId;
    }

    /** 获取cookie中的sessionId
     * @param request request请求域名
     * @param cookieKey cookie的键值
     * @return sessionId
     */
    public static String getValue(HttpServletRequest request, String cookieKey) {
        Cookie[] cookies = request.getCookies();
        String sessionId = "";
        if(cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieKey)){
                    sessionId = cookie.getValue();
                }
            }
        }
        return sessionId;
    }

    /**
     * 为实现 cookie + redis 单点登录，将 token 保存到 cookie 中
     * @param token 登录成功后生产token，规则为：用户账号_sessionId_yyyyMMddhhmmss(faker_jofiwejgkjzjop+eeoj_dekokp_20181122174526)
     * @return
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("cas_ticket", token);
        cookie.setMaxAge(60 * 60 * 60);

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Headers","content-type");
        response.addCookie(cookie);
    }

    public static String getLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        if(cookies != null && cookies.length > 0) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("ticket")){
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }
}
