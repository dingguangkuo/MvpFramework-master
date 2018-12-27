package com.guangkuo.mvpframework.data.remote.observer;

import com.guangkuo.mvpframework.base.BaseContract;
import com.guangkuo.mvpframework.data.remote.error.ApiException;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 对服务器返回异常、错误做统一处理
 */
public abstract class MyObserver<T, V extends BaseContract.BaseView> extends ErrorObserver<T> {
    private final V mView;

    public MyObserver(V view) {
        this.mView = view;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mView.showLoading();
    }

    @Override
    public void onNext(@NonNull T result) {
        mView.onLoadSuccess(result);
    }

    @Override
    protected void onError(ApiException ex) {
        mView.stopLoading();
        mView.onLoadFailed(ex);
    }

    @Override
    public void onComplete() {
        mView.stopLoading();
    }
}
