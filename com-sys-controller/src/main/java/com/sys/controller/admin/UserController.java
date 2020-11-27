package com.sys.controller.admin;

import com.sys.core.query.Query;
import com.sys.core.util.CollectUtils;
import com.sys.security.cas.CasProperty;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.model.admin.User;
import com.sys.service.admin.IUserService;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private CasProperty casProperty;

    @Override
    protected IService<User, String> getService() {
        return service;
    }

}
