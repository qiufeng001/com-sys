package com.sys.service.admin.impl;

import com.sys.model.admin.Menu;
import com.sys.domain.admin.MenuMapper;
import com.sys.service.admin.IMenuService;
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
 * @since 2020-05-27
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, String> implements IMenuService {

    @Autowired
    private MenuMapper mapper;

    protected IMapper<Menu, String> getMapper() {
        return mapper;
    }
}
