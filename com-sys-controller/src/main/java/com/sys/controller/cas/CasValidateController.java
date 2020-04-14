package com.sys.controller.cas;

import com.sys.core.configuration.Config;
import com.sys.core.inspect.ExecutionContext;
import com.sys.core.util.CollectUtils;
import com.sys.core.util.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.validation.AssertionImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 前后端cas单点登录配合不是很密切，这里用于前端严重是否登录
 *
 * @author z.h
 */
@Controller
@RequestMapping("/cas/*")
public class CasValidateController {

    @RequestMapping("/isLogin")
    @ResponseBody
    public Map<String, Object> casIsLogin(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> reuslt = CollectUtils.newHashMap();
        reuslt.put("isLogin", true);
        reuslt.put(Config.CAS_TICKET, CookieUtils.getValue(request, Config.CAS_TICKET));
        return reuslt;
    }

    @RequestMapping("/rediectToReact")
    public void rediectToReact(HttpServletResponse response) {
        try {
            response.sendRedirect("http://localhost?hasLogin=" + Boolean.TRUE);
        }catch (IOException e) {
            e.getMessage();
        }
    }
}
