package com.sys.model.admin;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sys.core.base.Entity;
import com.sys.core.dto.FrontEndFileDto;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-26
 */
public class Shop extends Entity {

    private static final long serialVersionUID=1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 负责人手机
     */
    private String phone;

    /**
     * 门店电话
     */
    private String tel;

    private List<FrontEndFileDto> files;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<FrontEndFileDto> getFiles() {
        return files;
    }

    public void setFiles(List<FrontEndFileDto> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Shop{" +
        "name=" + name +
        ", address=" + address +
        ", phone=" + phone +
        ", tel=" + tel +
        "}";
    }
}
