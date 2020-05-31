package com.sys.service.base.impl;

import com.sys.core.exception.ServiceException;
import com.sys.domain.base.CommonMapper;
import com.sys.model.admin.Menu;
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
}
