package com.sys.model.base;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sys.core.base.Entity;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhong.h
 * @since 2020-04-14
 */
@TableName("t_dictions")
public class Dictions extends Entity {

    private static final long serialVersionUID=1L;

    private String type;

    private String name;

    private String dictionsCode;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDictionsCode() {
        return dictionsCode;
    }

    public void setDictionsCode(String dictionsCode) {
        this.dictionsCode = dictionsCode;
    }

    @Override
    public String toString() {
        return "Dictions{" +
        "type=" + type +
        ", name=" + name +
        ", dictionsCode=" + dictionsCode +
        "}";
    }
}
