package com.guangkuo.mvpframework.data.remote.error;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Http 请求错误处理
 */
public class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(@NonNull Throwable throwable) {
        return Observable.error(ExceptionEngine.handleException(throwable)); // ExceptionEngine为处理异常的驱动器
    }
}
