package com.qlh.net;

/**
 * 作者：dell on 2018/5/17 09:38
 * 描述：基本实体类
 * T 为value的实体类型
 */

public class BaseBean<T> {

    private String errorResponse;
    private Boolean ok;
    private T value;

    public String getErrorResponse() {
        return errorResponse == null ? "" : errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public Boolean getOk() {
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