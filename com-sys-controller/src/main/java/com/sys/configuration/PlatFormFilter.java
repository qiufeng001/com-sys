package com.sys.configuration;

import com.sys.core.configuration.Config;
import com.sys.core.inspect.ExecutionContext;
import com.sys.core.util.CookieUtils;
import com.sys.core.util.DomainUtils;
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.validation.AssertionImpl;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;


/**
 * auther: kiven on 2018/9/7/007 16:11
 * try it bast!
 */
@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PlatFormFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HashMap<String, String> contextMap = new HashMap<>();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        request.setAttribute(Config.COOKIE_DOMAIN, getCookieDomain(request.getServerName()));

        // 如果是登录的话，获取登录的时间，设置登录时间，超过时间则退出登录，直接跳转到登录页面
        String requestURI = request.getRequestURI();

        if (requestURI.contains("/signout")) {
            ExecutionContext.setContextMap(null);
        }else{
            long startTime = System.currentTimeMillis();
            String currentThreadId = Thread.currentThread().getId() + "_" + CookieUtils.getLoginToken(request);
            contextMap.put(Config.LOGIN_START_TIME, startTime + "");
            contextMap.put(Config.WX_SESSION_ID, "wx_session_id_" + CookieUtils.getValue(request));
            contextMap.put(Config.USER_IP, request.getRemoteAddr());
            contextMap.put(Config.CONTEXT_PATH, request.getContextPath());
            contextMap.put(Config.CURRENT_THEAD_ID, currentThreadId);
            // cas ticket保存到cookie，供前端react使用
            String ticket = request.getParameter("ticket");
            if(StringUtils.isNotEmpty(ticket)) {
                CookieUtils.setCookie(request, response, ticket);
            }
//            contextMap.put(currentThreadId + "", "");
            ExecutionContext.setContextMap(contextMap);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private String getCookieDomain(String serverName) {
        String baseDomain = DomainUtils.getBaseDomain(serverName);
        return "." + baseDomain;
    }

    /**
     * 跨域问题解决（react 端加上无效）
     * @param response
     */
    private void setHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,token");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
    }
}
