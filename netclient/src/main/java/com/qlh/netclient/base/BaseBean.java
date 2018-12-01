package com.qlh.netclient.base;

/**
 * 作者：QLH on 2018/5/17 09:38
 *
 * 描述：基本实体类
 *
 * T 为value的实体类型
 *
 * 根据自己实际的数据结构进行改变
 */

public class BaseBean<T> {

    private String errorResponse;
    private int code;
    private Boolean ok;
    private T value;

    public String getErrorResponse() {
        return errorResponse == null ? "" : errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean isOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}