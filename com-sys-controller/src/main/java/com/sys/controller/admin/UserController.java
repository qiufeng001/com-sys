package com.sys.controller.admin;

import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.model.admin.User;
import com.sys.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * UserController
 *
 * @author zhong.h
 * @date 2019/11/1
 */
@Controller
@RequestMapping("/user/*")
public class UserController extends BaseController<User, String> {

    @Autowired
    private IUserService service;

    @Override
    protected IService<User, String> getService() {
        return service;
    }

    @Override
    protected String getTemplateFolder() {
        return "/admin/user";
    }

    @RequestMapping("test")
    @ResponseBody
    public String test() {
        return "test:";
    }
}
