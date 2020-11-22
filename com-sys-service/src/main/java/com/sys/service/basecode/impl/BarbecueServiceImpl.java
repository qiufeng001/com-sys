package com.sys.service.basecode.impl;

import com.sys.model.basecode.Barbecue;
import com.sys.domain.basecode.BarbecueMapper;
import com.sys.service.basecode.IBarbecueService;
import com.sys.core.service.impl.BaseServiceImpl;
import com.sys.core.domain.IMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 烧烤 服务实现类
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-19
 */
@Service
public class BarbecueServiceImpl extends BaseServiceImpl<Barbecue, String> implements IBarbecueService {

    @Autowired
    private BarbecueMapper mapper;

    protected IMapper<Barbecue, String> getMapper() {
        return mapper;
    }
}
