package com.sys.controller.admin;

import com.sys.core.query.Query;
import com.sys.core.util.CookieUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.commands.JedisCommands;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/home/*")
public class HomeController  {

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private Jedis jedis;

    @RequestMapping("/index")
    @ResponseBody
    public String index(HttpServletRequest request, Query query) {
        String sessionId = "sessionId: ";
        String cookieKey = CookieUtils.getValue(request, "sid");
        return sessionId + "jedis: " + jedis.get("shiro:session:" + cookieKey);
    }

    @RequestMapping("/err")
    @ResponseBody
    public String error() {
        return "error";
    }
}
