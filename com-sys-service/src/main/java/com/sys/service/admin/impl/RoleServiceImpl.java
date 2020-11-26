package com.sys.service.admin.impl;

import com.sys.model.admin.Role;
import com.sys.domain.admin.RoleMapper;
import com.sys.service.admin.IRoleService;
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
 * @since 2020-11-26
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements IRoleService {

    @Autowired
    private RoleMapper mapper;

    protected IMapper<Role, String> getMapper() {
        return mapper;
    }
}
