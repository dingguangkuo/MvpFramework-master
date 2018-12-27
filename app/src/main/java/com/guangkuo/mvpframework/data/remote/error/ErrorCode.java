package com.guangkuo.mvpframework.data.remote.error;

class ErrorCode {
    /**
     * 未知错误
     */
    static final int UNKNOWN;
    /**
     * 解析错误
     */
    static final int PARSE_ERROR;
    /**
     * 网络错误
     */
    static final int NETWORK_ERROR;
    /**
     * 协议出错
     */
    static final int HTTP_ERROR;

    static {
        UNKNOWN = 1000;
        PARSE_ERROR = 1001;
        NETWORK_ERROR = 1002;
        HTTP_ERROR = 1003;
    }
}