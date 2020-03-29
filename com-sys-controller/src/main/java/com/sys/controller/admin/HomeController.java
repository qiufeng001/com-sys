package com.sys.controller.admin;

import com.sys.core.query.Query;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.commands.JedisCommands;

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
    public String index( Query query) {
        String sessionId = "sessionId: ";
        Collection<Session> sessionList = sessionDAO.getActiveSessions();
        for (Session session : sessionList) {
            sessionId += session.getId() + ",";
        }
        return sessionId + "jedis: " + jedis.getClass();
    }

    @RequestMapping("/err")
    @ResponseBody
    public String error() {
        return "error";
    }
}
