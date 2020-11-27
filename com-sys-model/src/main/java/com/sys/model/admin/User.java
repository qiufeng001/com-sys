package com.sys.model.admin;

import com.sys.model.base.entity.Dictionary;
import com.sys.core.base.Entity;

/**
 * User
 *
 * @author zhong.h
 * @date 2019/10/31
 */
public class User extends Entity {

    /* 账号 */
    private String account;

    /** 状态(数据字典对应的对象) */
    private Dictionary dictionary;

    /** 状态 */
    private Integer status;

    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
