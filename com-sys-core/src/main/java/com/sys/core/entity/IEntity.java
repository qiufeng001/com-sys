package com.sys.core.entity;

import java.util.Date;

/**
 * IUser
 *
 * @author zhong.h
 * @date 2019/10/31
 */
public interface IEntity<T> {
    // id 主键
    T getId();

    void setId(T val);

    // 名称
    void setName(String name);

    String getName();
    // 创建时间
    Date getCreatedDate();

    void setCreatedDate(Date date);
    // 修改时间
    Date getUpdatedDate();

    void setUpdatedDate(Date date);
}
