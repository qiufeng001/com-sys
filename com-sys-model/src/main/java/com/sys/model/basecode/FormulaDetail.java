package com.sys.model.basecode;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sys.core.base.Entity;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-10
 */
public class FormulaDetail extends Entity {

    private static final long serialVersionUID=1L;

    /**
     * 明细对应的配方主表id
     */
    private String formulaId;

    /**
     * 材料
     */
    private String materialId;

    /**
     * 材料名
     */
    private String materialName;

    /**
     * 配比
     */
    private String preparateRate;

    /**
     * 说明
     */
    private String remark;


    public String getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(String formulaId) {
        this.formulaId = formulaId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getPreparateRate() {
        return preparateRate;
    }

    public void setPreparateRate(String preparateRate) {
        this.preparateRate = preparateRate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Override
    public String toString() {
        return "FormulaDetail{" +
        "formulaId=" + formulaId +
        ", materialId=" + materialId +
        ", preparateRate=" + preparateRate +
        ", remark=" + remark +
        "}";
    }
}
