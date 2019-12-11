package com.sys.service.admin.impl;

import com.sys.core.domain.IRepository;
import com.sys.core.service.impl.BaseServiceImpl;
import com.sys.domain.admin.UserRepository;
import com.sys.model.admin.User;
import com.sys.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author zhong.h
 * @date 2019/11/1
 */
@SuppressWarnings("ALL")
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    protected IRepository getRepository() {
        return repository;
    }
}