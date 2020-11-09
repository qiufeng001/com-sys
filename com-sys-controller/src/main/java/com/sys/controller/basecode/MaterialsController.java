package com.sys.controller.basecode;

import com.sys.model.basecode.Materials;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.service.basecode.IMaterialsService;

/**
 * <p>
 * 材料表 前端控制器
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/materials/*")
public class MaterialsController extends BaseController<Materials, String> {

    @Autowired
    private IMaterialsService service;

    @Override
    protected IService<Materials, String> getService() {
        return service;
    }
}

