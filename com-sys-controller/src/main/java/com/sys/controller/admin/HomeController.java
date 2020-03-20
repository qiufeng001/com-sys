package com.sys.controller.admin;

import com.sys.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/home/*")
public class HomeController  {

    @RequestMapping("/index")
    @ResponseBody
    public String index( Query query) {
        return "index";
    }
}
