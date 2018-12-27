package com.guangkuo.mvpframework.data.remote.observer;

import com.guangkuo.mvpframework.data.remote.error.ApiException;

import io.reactivex.Observer;

abstract class ErrorObserver<T> implements Observer<T> {
    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            // TODO: 需要做统一处理的错误代码放在这里处理
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, -100));// 其他异常的处理
        }
        e.printStackTrace();
    }

    /**
     * 错误回调
     */
    abstract void onError(ApiException ex);
}