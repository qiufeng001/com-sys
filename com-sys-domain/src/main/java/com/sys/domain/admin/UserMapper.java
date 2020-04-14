package com.sys.domain.admin;

import com.sys.core.domain.IMapper;
import com.sys.model.admin.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserRepository
 *
 * @author zhong.h
 * @date 2019/10/31
 */
@Mapper
public interface UserMapper extends IMapper<User, String> {

}
