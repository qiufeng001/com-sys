package com.sys.controller.base;

import com.sys.model.base.Dictions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.service.base.IDictionsService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhong.h
 * @since 2020-04-15
 */
@RestController
@RequestMapping("/dictions/*")
public class DictionsController extends BaseController<Dictions, String> {

    @Autowired
    private IDictionsService service;

    @Override
    protected IService<Dictions, String> getService() {
        return service;
    }
}

