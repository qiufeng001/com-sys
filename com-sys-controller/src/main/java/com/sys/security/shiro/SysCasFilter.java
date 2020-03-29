package com.sys.security.shiro;

import com.sys.core.util.CookieUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 重写CasFilter，这里是因为如果是GET请求的话，在cas登录的时候，使用的是get请求，因此会出现，你访问时用的路径就是shiro登录成功后访问的路径，
 *
 * @author zhong.h
 */
public class SysCasFilter extends CasFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //清空原有路径 跳转到下一个路径
        this.issueSuccessRedirect(request, response);
        return false;
    }

    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        this.redirectToSavedRequest(request, response, this.getSuccessUrl());
    }

    public static void redirectToSavedRequest(ServletRequest request, ServletResponse response, String fallbackUrl) throws IOException {
        String successUrl = null;
        boolean contextRelative = true;
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        // 这里如果是GET请求的话，在cas登录的时候，使用的是get请求，因此会出现，你访问时用的路径就是shiro登录成功后访问的路径，
        // 而不是自己设置的路径，因此注释这段代码，强制走自己设定的路径
        /*if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
            successUrl = savedRequest.getRequestUrl();
            contextRelative = false;
        }*/

        if (successUrl == null) {
            successUrl = fallbackUrl;
        }

        if (successUrl == null) {
            throw new IllegalStateException("Success URL not available via saved request or via the successUrlFallback method parameter. One of these must be non-null for issueSuccessRedirect() to work.");
        } else {
            WebUtils.issueRedirect(request, response, successUrl, (Map)null, contextRelative);
        }
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        // 这里往cookie中设置ticket
        String queryString = servletRequest.getQueryString();
        // 设置ticket
        if(queryString != null && queryString.contains("ticket")){
            String ticket = queryString.substring(queryString.indexOf("=") + 1);
            CookieUtils.setCookie(servletRequest, servletResponse, ticket);
        }
        HttpSession session = ((HttpServletRequest) request).getSession();
        System.out.println("sessionID1：" + session.getId());
        // 创建shiro绘画
        Session session2 = SecurityUtils.getSubject().getSession();
        System.out.println("sessionID2：" + session2.getId());
    }
}
