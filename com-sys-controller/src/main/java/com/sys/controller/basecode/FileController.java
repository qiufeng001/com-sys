package com.sys.controller.basecode;

import com.sys.model.basecode.File;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.service.basecode.IFileService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-11
 */
@RestController
@RequestMapping("/file/*")
public class FileController extends BaseController<File, String> {

    @Autowired
    private IFileService service;

    @Override
    protected IService<File, String> getService() {
        return service;
    }
}

