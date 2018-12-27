package com.guangkuo.mvpframework.module.user;

import com.guangkuo.mvpframework.base.BaseContract;

public interface LoginContract {
    interface View extends BaseContract.BaseView {
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        /**
         * 登录用户
         *
         * @param account  用户名
         * @param password 密码
         */
        void login(String account, String password);
    }
}