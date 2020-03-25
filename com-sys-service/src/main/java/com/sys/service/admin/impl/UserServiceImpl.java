package com.sys.service.admin.impl;

import com.sys.core.domain.IRepository;
import com.sys.core.service.impl.BaseServiceImpl;
import com.sys.domain.admin.UserRepository;
import com.sys.model.admin.User;
import com.sys.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private UserRepository userRepository;

    public UserServiceImpl() {
        System.out.println("UserServiceImpl");

    }

    @Override
    protected IRepository getRepository() {
        return userRepository;
    }

    public User findByParams(Map<String, Object> params) {
        return userRepository.findByParam(params);
    }
}