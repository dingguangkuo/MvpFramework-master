package com.guangkuo.mvpframework.app;

import android.app.Application;

import com.guangkuo.mvpframework.di.app.AppModule;
import com.guangkuo.mvpframework.di.app.DaggerNetComponent;
import com.guangkuo.mvpframework.di.app.NetComponent;
import com.guangkuo.mvpframework.di.app.NetModule;

/**
 * Application
 * <p>
 * Created by Guangkuo on 2018/12/21.
 */
public class MyApplication extends Application {
    private final String baseUrl = "http://xxxxxxxx.com:8080";
    private NetComponent mNetComponent;// 这些实例在整个application生命周期中只会被实例化一次

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(baseUrl)).build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
