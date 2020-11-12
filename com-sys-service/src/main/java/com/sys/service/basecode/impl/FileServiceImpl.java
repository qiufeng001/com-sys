package com.sys.service.basecode.impl;

import com.sys.model.basecode.File;
import com.sys.domain.basecode.FileMapper;
import com.sys.service.basecode.IFileService;
import com.sys.core.service.impl.BaseServiceImpl;
import com.sys.core.domain.IMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-11
 */
@Service
public class FileServiceImpl extends BaseServiceImpl<File, String> implements IFileService {

    @Autowired
    private FileMapper mapper;

    protected IMapper<File, String> getMapper() {
        return mapper;
    }
}
