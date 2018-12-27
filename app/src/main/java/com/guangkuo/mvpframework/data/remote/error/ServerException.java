package com.guangkuo.mvpframework.data.remote.error;

/**
 * 定义服务器异常
 */
public class ServerException extends RuntimeException {
    public int result;
    public String message;

    public ServerException(int result, String message) {
        this.result = result;
        this.message = message;
    }
}
