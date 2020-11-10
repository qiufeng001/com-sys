package com.sys.service.basecode.impl;

import com.sys.model.basecode.FormulaDetail;
import com.sys.domain.basecode.FormulaDetailMapper;
import com.sys.service.basecode.IFormulaDetailService;
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
 * @since 2020-11-10
 */
@Service
public class FormulaDetailServiceImpl extends BaseServiceImpl<FormulaDetail, String> implements IFormulaDetailService {

    @Autowired
    private FormulaDetailMapper mapper;

    protected IMapper<FormulaDetail, String> getMapper() {
        return mapper;
    }
}
