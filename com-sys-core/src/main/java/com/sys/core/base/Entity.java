package com.sys.core.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sys.core.dto.FrontEndFileDto;
import com.sys.core.entity.IEntity;
import com.sys.core.util.date.JsonDateDeserializer;
import com.sys.core.util.date.JsonDefaultDateSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Entity 基础类，默认主键为 String 类型
 *
 * @author zhong.h
 * @date 2019/10/31
 */
public abstract class Entity implements Serializable, IEntity<String> {

    private String id;

    private String name;

    /** 创建时间 */
    @JsonSerialize(using = JsonDefaultDateSerializer.class) // 日期序列号
    @JsonDeserialize(using = JsonDateDeserializer.class) //
    private Date createTime;

    /** 创建时间 */
    @JsonSerialize(using = JsonDefaultDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date updateTime;

    /** 创建者，因为无论是哪一个用户，都会有创建者和修改者，因此，将这个抽出来，公用 */
    private String createUser;

    /** 更新者 ，因为无论是哪一个用户，都会有创建者和修改者，因此，将这个抽出来，公用*/
    private String updateUser;

    private List<FrontEndFileDto> files;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public List<FrontEndFileDto> getFiles() {
        return files;
    }

    public void setFiles(List<FrontEndFileDto> files) {
        this.files = files;
    }
}
