package com.sys.controller.basecode;

import com.sys.model.basecode.Barbecue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.service.basecode.IBarbecueService;

/**
 * <p>
 * 烧烤 前端控制器
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-19
 */
@RestController
@RequestMapping("/barbecue/*")
public class BarbecueController extends BaseController<Barbecue, String> {

    @Autowired
    private IBarbecueService service;

    @Override
    protected IService<Barbecue, String> getService() {
        return service;
    }
}

