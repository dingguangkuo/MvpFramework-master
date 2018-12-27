package com.guangkuo.mvpframework.data.remote.error;

/**
 * 自定义 ApiException
 */
public class ApiException extends Exception {
    public int result;
    public String message;

    public ApiException(Throwable throwable, int result) {
        super(throwable);
        this.result = result;
    }
}
