package com.sys.controller.admin;

import com.sys.core.controller.impl.BaseController;
import com.sys.core.query.Query;
import com.sys.core.service.IService;
import com.sys.model.admin.Menu;
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
