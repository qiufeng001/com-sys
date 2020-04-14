package com.sys.controller.admin;

import com.sys.core.controller.impl.BaseController;
import com.sys.core.query.Query;
import com.sys.core.service.IService;
import com.sys.core.util.CookieUtils;
import com.sys.model.admin.Menu;
import com.sys.service.admin.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/*")
public class HomeController extends BaseController<Menu, String> {

    @Autowired
    private IMenuService menuService;

    @Override
    protected IService<Menu, String> getService() {
        return menuService;
    }

    @RequestMapping("/index")
    @ResponseBody
    public String index(HttpServletRequest request, Query query) {
        String sessionId = "sessionId: ";
        String cookieKey = CookieUtils.getValue(request, "sid");
        return sessionId + "jedis: ";
    }

    @RequestMapping("/err")
    @ResponseBody
    public String error() {
        return "error";
    }
}
