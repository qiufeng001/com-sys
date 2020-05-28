package com.sys.service.base.impl;

import com.sys.model.base.SysGroups;
import com.sys.domain.base.SysGroupsMapper;
import com.sys.service.base.ISysGroupsService;
import com.sys.core.service.impl.BaseServiceImpl;
import com.sys.core.domain.IMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 系统组 服务实现类
 * </p>
 *
 * @author zhong.h
 * @since 2020-05-27
 */
@Service
public class SysGroupsServiceImpl extends BaseServiceImpl<SysGroups, String> implements ISysGroupsService {

    @Autowired
    private SysGroupsMapper mapper;

    protected IMapper<SysGroups, String> getMapper() {
        return mapper;
    }
}
