package com.sys.controller.cas;

import com.sys.core.configuration.Config;
import com.sys.core.inspect.ExecutionContext;
import com.sys.core.util.CollectUtils;
import com.sys.core.util.CookieUtils;
import com.sys.model.admin.User;
import com.sys.security.cas.CasProperty;
import com.sys.util.HttpClientPostFs;
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.AssertionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 前后端cas单点登录配合不是很密切，这里用于前端验证是否登录
 *
 * @author z.h
 */
@Controller
@RequestMapping("/cas/*")
public class CasValidateController {

    @Autowired
    private CasProperty casProperty;

    @RequestMapping("/isLogin")
    @ResponseBody
    public Map<String, Object> casIsLogin(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> reuslt = CollectUtils.newHashMap();
        reuslt.put("isLogin", true);
        reuslt.put(Config.CAS_TICKET, CookieUtils.getValue(request, Config.CAS_TICKET));
        return reuslt;
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
