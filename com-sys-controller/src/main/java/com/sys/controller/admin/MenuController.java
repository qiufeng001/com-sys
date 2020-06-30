package com.sys.controller.admin;

import com.sys.core.query.Query;
import com.sys.model.admin.Menu;
import com.sys.util.MenuUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.service.admin.IMenuService;

import java.util.List;

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

    @ResponseBody
    @RequestMapping("/menus")
    private List<Menu> getMenus(Query query) {
        List<Menu> rootMenus = service.selectByParams(query);
        List<Menu> menus = MenuUtils.getMenu(rootMenus);
        return menus;
    }

}

