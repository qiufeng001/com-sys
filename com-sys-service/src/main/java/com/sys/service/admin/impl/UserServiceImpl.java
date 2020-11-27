package com.sys.service.admin.impl;

import com.sys.core.domain.IMapper;
import com.sys.core.dto.FrontEndFileDto;
import com.sys.core.entity.File;
import com.sys.core.exception.ServiceException;
import com.sys.core.query.Query;
import com.sys.core.service.impl.BaseServiceImpl;
import com.sys.core.util.CollectUtils;
import com.sys.core.util.DESUtils;
import com.sys.domain.admin.UserMapper;
import com.sys.model.admin.User;
import com.sys.service.admin.IUserService;
import com.sys.service.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
    private String algorithm = "MD5";

    @Override
    protected IMapper getMapper() {
        return mapper;
    }

    public User findByParams(Map<String, Object> params) {
        return mapper.findByParam(params);
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer insert(User entry, HttpServletRequest request) {
        initEntry(entry, request);
        try {
            // 新建用户，默认密码是6个1
            entry.setPassword(DESUtils.encode("111111", algorithm));
            return getMapper().insert(entry);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public User getUser(Query query) {
        List<User> users = mapper.selectByParams(query.asMap(), "");
        if(CollectUtils.isNotEmpty(users)) {
            User user = users.get(0);
            List<File> files = getMapper().selectFileByEntityId(user.getId());
            if(CollectUtils.isNotEmpty(files)) {
                List<FrontEndFileDto> dtos = CollectUtils.newArrayList();
                for(File file : files) {
                    FrontEndFileDto dto = FileUtils.encodeBase64(file);
                    dtos.add(dto);
                }
                user.setFiles(dtos);
            }
            return user;
        }
        return null;
    }
}