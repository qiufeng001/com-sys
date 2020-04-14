package com.sys.service.admin.impl;

import com.sys.core.domain.IMapper;
import com.sys.core.service.impl.BaseServiceImpl;
import com.sys.domain.admin.MenuMapper;
import com.sys.model.admin.Menu;
import com.sys.service.admin.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, String> implements IMenuService {

    @Autowired
    private MenuMapper mapper;

    @Override
    protected IMapper getMapper() {
        return mapper;
    }
}
