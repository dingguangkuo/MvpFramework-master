package com.guangkuo.mvpframework.base;

import com.guangkuo.mvpframework.data.remote.error.ApiException;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * 协议类
 */
public interface BaseContract {
    interface BasePresenter<T extends BaseContract.BaseView> {
        void attachView(T view);

        void detachView();
    }

    interface BaseView {
        /**
         * 显示加载框
         */
        void showLoading();

        /**
         * 隐藏加载框
         */
        void stopLoading();

        /**
         * 加载失败
         *
         * @param ex
         */
        void onLoadFailed(ApiException ex);

        /**
         * 加载成功
         *
         * @param result 服务器返回数据
         */
        void onLoadSuccess(Object result);

        /**
         * 显示当前网络不可用
         */
        void showNoNet();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();

        /**
         * 跳转到登录页面
         */
        void jumpToLogin();
    }
}
