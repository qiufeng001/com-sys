package com.sys.model.basecode;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sys.core.base.Entity;

/**
 * <p>
 * 材料表
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-07
 */
public class Materials extends Entity {

    private static final long serialVersionUID=1L;

    private String name;

    /**
     * 属性
     */
    private String attribute;

    /**
     * 功效
     */
    private String efficacy;

    /**
     * 说明
     */
    private String instructions;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getEfficacy() {
        return efficacy;
    }

    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Materials{" +
        "name=" + name +
        ", attribute=" + attribute +
        ", efficacy=" + efficacy +
        ", instructions=" + instructions +
        "}";
    }
}
