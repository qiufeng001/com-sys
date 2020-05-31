package com.sys.controller.base;

import com.sys.model.base.SysGroups;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.service.base.ISysGroupsService;

/**
 * <p>
 * 系统组 前端控制器
 * </p>
 *
 * @author zhong.h
 * @since 2020-05-27
 */
@RestController
@RequestMapping("/sysGroups/*")
public class SysGroupsController extends BaseController<SysGroups, String> {

    @Autowired
    private ISysGroupsService service;

    @Override
    protected IService<SysGroups, String> getService() {
        return service;
    }
}

