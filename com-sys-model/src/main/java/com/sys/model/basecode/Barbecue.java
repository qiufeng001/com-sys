package com.sys.model.basecode;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sys.core.base.Entity;

/**
 * <p>
 * 烧烤
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-19
 */
public class Barbecue extends Entity {

    private static final long serialVersionUID=1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 烤制方式方法
     */
    private String method;

    /**
     * 配料
     */
    private String ingredients;

    /**
     * 说明
     */
    private String remark;

    /**
     * 标签
     */
    private String tag;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Barbecue{" +
        "name=" + name +
        ", method=" + method +
        ", ingredients=" + ingredients +
        ", remark=" + remark +
        ", tag=" + tag +
        "}";
    }
}
