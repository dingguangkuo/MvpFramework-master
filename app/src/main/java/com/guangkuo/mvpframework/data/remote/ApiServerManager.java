package com.guangkuo.mvpframework.data.remote;

import com.guangkuo.mvpframework.base.BaseContract;
import com.guangkuo.mvpframework.data.bean.DataResponse;
import com.guangkuo.mvpframework.data.bean.User;
import com.guangkuo.mvpframework.data.remote.api.ApiService;
import com.guangkuo.mvpframework.data.remote.error.HttpResponseFunc;
import com.guangkuo.mvpframework.data.remote.error.ServerResponseFunc;
import com.guangkuo.mvpframework.data.remote.observer.MyObserver;
import com.guangkuo.mvpframework.data.remote.observer.RetryWithDelay;
import com.guangkuo.mvpframework.utils.RxSchedulers;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;

public class ApiServerManager {
    private ApiService mApiService;
    private static ApiServerManager mApiServerManager;

    private static final int RETRY_COUNT = 2;// 失败重试次数
    private static final int RETRY_DELAY = 3000;// 失败重试延时（ms）

    /**
     * 单例
     *
     * @return ApiServerManager 实例
     */
    public static ApiServerManager getInstance() {
        if (mApiServerManager == null) {
            synchronized (ApiServerManager.class) {
                if (mApiServerManager == null) {
                    mApiServerManager = new ApiServerManager();
                }
            }
        }
        return mApiServerManager;
    }

    private ApiServerManager() {
        mApiService = RetrofitManager.create(ApiService.class);
    }

    /**
     * Observable 管理
     *
     * @param observable 被观察者
     * @param myObserver 观察者
     * @param <T>        泛型参数
     */
    private <T, V extends BaseContract.BaseView> void observableManage(Observable<DataResponse<T>> observable,
                                                                       LifecycleTransformer<DataResponse<T>> lifecycle,
                                                                       MyObserver<T, V> myObserver) {
        observable
                .compose(lifecycle)
                .compose(RxSchedulers.<DataResponse<T>>applySchedulers())
                .retryWhen(new RetryWithDelay(RETRY_COUNT, RETRY_DELAY))
                .map(new ServerResponseFunc<T>())
                .onErrorResumeNext(new HttpResponseFunc<T>())
                .subscribe(myObserver);
    }

    /**
     * 注册
     */
    public <V extends BaseContract.BaseView> void login(String username, String password,
                                                        LifecycleTransformer<DataResponse<User>> lifecycle, MyObserver<User, V> myObserver) {
        this.observableManage(mApiService.login(username, password), lifecycle, myObserver);
    }
}