package com.sys.model.admin;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.List;

import com.sys.core.base.Entity;
import com.sys.core.util.CollectUtils;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhong.h
 * @since 2020-05-27
 */
public class Menu extends Entity {

    private static final long serialVersionUID=1L;

    private String code;

    private Integer status;

    private String pId;

    private String url;

    private String sequence;

    private List<Menu> childMenus = CollectUtils.newArrayList();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }
        
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public List<Menu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<Menu> childMenus) {
        this.childMenus = childMenus;
    }
}
