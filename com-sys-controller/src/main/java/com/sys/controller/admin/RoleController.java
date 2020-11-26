package com.sys.controller.admin;

import com.sys.model.admin.Role;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.service.admin.IRoleService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-26
 */
@RestController
@RequestMapping("/role/*")
public class RoleController extends BaseController<Role, String> {

    @Autowired
    private IRoleService service;

    @Override
    protected IService<Role, String> getService() {
        return service;
    }
}

