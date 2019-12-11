package com.sys.model.base.entity;

import com.sys.core.entity.IEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity 基础类（主键是字符串）
 *
 * @author zhong.h
 * @date 2019/10/31
 */
public class StringEntity implements Serializable, IEntity<String> {

    private String id;

    private String name;

    private Date createdDate;

    private Date updatedDate;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String val) {
        this.id = val;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Date date) {
        this.createdDate = createdDate;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(Date date) {
        this.updatedDate = updatedDate;
    }
}
