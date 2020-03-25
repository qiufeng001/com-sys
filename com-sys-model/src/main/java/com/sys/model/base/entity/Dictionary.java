package com.sys.model.base.entity;

/**
 * 基础数据字段
 *
 * @author zhong.hui
 */
public class Dictionary extends Entity {

    /** 说明 */
    private String explain;

    /** 类型 */
    private Type type;

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
