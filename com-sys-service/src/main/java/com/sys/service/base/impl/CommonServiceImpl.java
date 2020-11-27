package com.sys.service.base.impl;

import com.sys.core.exception.ServiceException;
import com.sys.core.query.Q;
import com.sys.core.query.Query;
import com.sys.core.query.Statement;
import com.sys.domain.base.CommonMapper;
import com.sys.model.admin.Menu;
import com.sys.model.base.Common;
import com.sys.service.base.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhong.h
 * @since 2020-04-14
 */
@Service
public class CommonServiceImpl implements ICommonService {

    @Autowired
    private CommonMapper mapper;

    @Override
    public List<Menu> getAccountMenu(String account) {
        try {
            return mapper.getAccountMenu(account);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer validate(Query query) {
        try {
            return mapper.validate(query.asMap());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
