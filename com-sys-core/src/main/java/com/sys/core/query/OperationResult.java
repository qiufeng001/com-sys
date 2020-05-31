package com.sys.core.query;

import java.io.Serializable;

/**
 * 处理结果对象
 *
 * @author zhong.h
 */
public class OperationResult<T> implements Serializable {

    /** 处理结果状态 */
    private boolean status;
    /** 处理信息 */
    private String msg;
    /** 处理后返回数据 */
    private T data;
    /** 处理总数 */
    private Integer count;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
