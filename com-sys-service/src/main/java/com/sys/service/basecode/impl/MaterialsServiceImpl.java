package com.sys.service.basecode.impl;

import com.sys.model.basecode.Materials;
import com.sys.domain.basecode.MaterialsMapper;
import com.sys.service.basecode.IMaterialsService;
import com.sys.core.service.impl.BaseServiceImpl;
import com.sys.core.domain.IMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 材料表 服务实现类
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-07
 */
@Service
public class MaterialsServiceImpl extends BaseServiceImpl<Materials, String> implements IMaterialsService {

    @Autowired
    private MaterialsMapper mapper;

    protected IMapper<Materials, String> getMapper() {
        return mapper;
    }
}
