package com.guangkuo.mvpframework.module.user;

import com.guangkuo.mvpframework.base.BasePresenter;
import com.guangkuo.mvpframework.data.bean.DataResponse;
import com.guangkuo.mvpframework.data.bean.User;
import com.guangkuo.mvpframework.data.remote.ApiServerManager;
import com.guangkuo.mvpframework.data.remote.error.ApiException;
import com.guangkuo.mvpframework.data.remote.observer.MyObserver;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    @Inject
    public LoginPresenter() {
    }

    @Override
    public void login(String account, String password) {
        ApiServerManager.getInstance()
                .login(account, password, mView.<DataResponse<User>>bindToLife(),
                        new MyObserver<User, LoginContract.View>(mView) {
                            @Override
                            public void onNext(User result) {
                                super.onNext(result);
                            }

                            @Override
                            protected void onError(ApiException ex) {
                                super.onError(ex);
                            }
                        });
    }
}
