package com.sys.model.basecode;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sys.core.base.Entity;
import com.sys.core.util.CollectUtils;

import java.util.List;

/**
 * <p>
 * 配方
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-10
 */
public class Formula extends Entity {

    private static final long serialVersionUID=1L;


    /**
     * 名称
     */
    private String name;

    /**
     * 步骤
     */
    private String steps;

    /**
     * 说明
     */
    private String remark;

    private List<FormulaDetail> details;


    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<FormulaDetail> getDetails() {
        return details;
    }

    public void setDetails(List<FormulaDetail> details) {
        this.details = details;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Formula{" +
        "steps=" + steps +
        ", remark=" + remark +
        "}";
    }
}
