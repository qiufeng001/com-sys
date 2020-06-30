package com.sys.controller.cas;

import com.sys.core.configuration.Config;
import com.sys.core.util.CollectUtils;
import com.sys.core.util.CookieUtils;
import com.sys.security.cas.CasProperty;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 前后端cas单点登录配合不是很密切，这里用于前端验证是否登录
 *
 * @author z.h
 */
@Controller
@RequestMapping("/cas/*")
public class CasController {

    @Autowired
    private CasProperty casProperty;


    /**
     * cas 服务端配置了单点退出配置
     *
     * @param session
     * @param response
     */
    @RequestMapping("/logout")
    @ResponseBody
    public void casIsLogin(HttpSession session, HttpServletResponse response) {
        // 退出业务
        session.removeAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        session.invalidate();
        try {
            // 重定向到登录页
            Thread.sleep(1000);
            response.sendRedirect(casProperty.getLogoutUrl() + "?service=" + casProperty.getShiroServerUrlPrefix());
        }catch (IOException e) {
            e.getMessage();
        }catch (InterruptedException e2) {
            e2.getMessage();
        }
    }

    @RequestMapping("/loginUser")
    @ResponseBody
    public Map<String, Object> getLoginUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> reuslt = CollectUtils.newHashMap();

        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        reuslt.put("msg", principal.getName());
        return reuslt;
    }

    @RequestMapping("/redirectToReact")
    public void rediectToReact(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.sendRedirect(casProperty.getShiroServerUrlPrefix());
        }catch (IOException e) {
            e.getMessage();
        }
    }
}
