package com.sys.model.base.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 基础数据字段
 *
 * @author zhong.hui
 */
@Getter
@Setter
public class Dictionary extends Entity {

    /** 说明 */
    private String explain;

    /** 类型 */
    private Type type;
}
