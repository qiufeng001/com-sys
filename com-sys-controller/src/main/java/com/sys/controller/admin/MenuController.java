package com.sys.controller.admin;

import com.sys.model.admin.Menu;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.service.admin.IMenuService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhong.h
 * @since 2020-05-27
 */
@RestController
@RequestMapping("/menu/*")
public class MenuController extends BaseController<Menu, String> {

    @Autowired
    private IMenuService service;

    @Override
    protected IService<Menu, String> getService() {
        return service;
    }
}

