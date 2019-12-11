package com.sys.model.base.entity;

import com.sys.core.entity.IEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * IntegerEntity 基础类（主键是Long类型）
 *
 * @author zhong.h
 * @date 2019/10/31
 */
public class LongEntity implements Serializable, IEntity<Long> {

    private Long id;

    private String name;

    private Date createdDate;

    private Date updatedDate;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long val) {
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
