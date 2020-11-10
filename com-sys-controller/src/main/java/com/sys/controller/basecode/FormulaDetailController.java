package com.sys.controller.basecode;

import com.sys.model.basecode.FormulaDetail;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.service.basecode.IFormulaDetailService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-10
 */
@RestController
@RequestMapping("/formulaDetail/*")
public class FormulaDetailController extends BaseController<FormulaDetail, String> {

    @Autowired
    private IFormulaDetailService service;

    @Override
    protected IService<FormulaDetail, String> getService() {
        return service;
    }
}

