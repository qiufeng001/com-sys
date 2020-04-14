package com.sys.service.base.impl;

import com.sys.model.base.Dictions;
import com.sys.domain.base.DictionsMapper;
import com.sys.service.base.IDictionsService;
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
 * @since 2020-04-14
 */
@Service
public class DictionsServiceImpl extends BaseServiceImpl<Dictions, String> implements IDictionsService {

    @Autowired
    private DictionsMapper mapper;

    protected IMapper<Dictions, String> getMapper() {
        return mapper;
    }
}
