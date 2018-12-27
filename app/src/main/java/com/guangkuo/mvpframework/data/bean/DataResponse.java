package com.guangkuo.mvpframework.data.bean;

/**
 * 响应数据
 * <p>
 * Created by Guangkuo on 2018/12/25.
 */
public class DataResponse<T> {
    private int result;
    private String message;
    private T data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
