package com.sys.model.base;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sys.core.base.Entity;

/**
 * <p>
 * 系统组
 * </p>
 *
 * @author zhong.h
 * @since 2020-05-27
 */
@TableName("t_sys_groups")
public class SysGroups extends Entity {

    private static final long serialVersionUID=1L;

    /**
     * 组名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SysGroups{" +
        "name=" + name +
        ", code=" + code +
        "}";
    }
}
