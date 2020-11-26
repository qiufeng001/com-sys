package com.sys.controller.admin;

import com.sys.model.admin.Shop;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.service.admin.IShopService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-26
 */
@RestController
@RequestMapping("/shop/*")
public class ShopController extends BaseController<Shop, String> {

    @Autowired
    private IShopService service;

    @Override
    protected IService<Shop, String> getService() {
        return service;
    }
}

