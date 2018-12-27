package com.guangkuo.mvpframework.data.remote.observer;

import com.blankj.utilcode.util.LogUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class RetryWithDelay implements
        Function<Observable<? extends Throwable>, Observable<?>> {
    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    /**
     * 构造函数
     *
     * @param maxRetries       最多重试次数
     * @param retryDelayMillis 重试时间间隔
     */
    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) {
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) {
                if (++retryCount <= maxRetries) {
                    LogUtils.i("Get error, it will try after " + retryDelayMillis + " millisecond, retry count " + retryCount);
                    return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                }
                return Observable.error(throwable);
            }
        });
    }
}
