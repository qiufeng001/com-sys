package com.sys.service.admin.impl;

import com.sys.core.domain.IMapper;
import com.sys.core.service.impl.BaseServiceImpl;
import com.sys.domain.admin.UserMapper;
import com.sys.model.admin.User;
import com.sys.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * UserServiceImpl
 *
 * @author zhong.h
 * @date 2019/11/1
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements IUserService {

    @Autowired
    private UserMapper mapper;

    @Override
    protected IMapper getMapper() {
        return mapper;
    }

    public User findByParams(Map<String, Object> params) {
        return mapper.findByParam(params);
    }
}