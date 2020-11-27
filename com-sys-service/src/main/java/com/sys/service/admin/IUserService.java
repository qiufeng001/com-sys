package com.sys.service.admin;

import com.sys.core.query.Query;
import com.sys.core.service.IService;
import com.sys.model.admin.User;

import java.util.Map;

/**
 * IUserService
 *
 * @author zhong.h
 * @date 2019/11/1
 */
public interface IUserService extends IService<User, String> {
    User findByParams(Map<String, Object> params);
    User getUser(Query query);
}
