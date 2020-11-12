package com.sys.model.basecode;

import com.sys.core.base.Entity;
import com.sys.core.dto.FrontEndFileDto;

import java.util.List;

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

    private List<FrontEndFileDto> files;


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

    public List<FrontEndFileDto> getFiles() {
        return files;
    }

    public void setFiles(List<FrontEndFileDto> files) {
        this.files = files;
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
