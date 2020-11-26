package com.sys.core.entity;

import com.sys.core.dto.FrontEndFileDto;

import java.util.Date;
import java.util.List;

/**
 * IUser
 *
 * @author zhong.h
 * @date 2019/10/31
 */
public interface IEntity<K> {
    // id 主键
    K getId();

    void setId(K val);

    // 名称
    void setName(String name);

    String getName();
    // 创建时间
    Date getCreateTime();

    void setCreateTime(Date date);
    // 修改时间
    Date getUpdateTime();

    void setUpdateTime(Date date);

    String getCreateUser();

    void setCreateUser(String createUser);

    String getUpdateUser();

    void setUpdateUser(String updateUser);

    List<FrontEndFileDto> getFiles();

    void setFiles(List<FrontEndFileDto> files);
}
