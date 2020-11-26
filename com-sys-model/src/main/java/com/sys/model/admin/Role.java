package com.sys.model.admin;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.sys.core.base.Entity;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-26
 */
public class Role extends Entity {

    private static final long serialVersionUID=1L;

    private String roleNo;

    private String name;

    private Integer status;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;


    public String getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "Role{" +
        "roleNo=" + roleNo +
        ", name=" + name +
        ", status=" + status +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
