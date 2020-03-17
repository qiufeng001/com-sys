package com.sys.model.admin;

import com.sys.model.base.entity.Dictionary;
import com.sys.model.base.entity.Entity;
import lombok.Getter;
import lombok.Setter;

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
}
