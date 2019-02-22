package com.guangkuo.mvpframework.app;

import android.app.Application;

import com.guangkuo.mvpframework.di.components.AppComponent;
import com.guangkuo.mvpframework.di.components.DaggerAppComponent;
import com.guangkuo.mvpframework.di.modules.AppModule;

/**
 * Application
 * <p>
 * Created by Guangkuo on 2018/12/25.
 */
public class App extends Application {
    private static App mInstance;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initComponent();// 初始化组建
    }

    public static App getInstance() {
        return mInstance;
    }

    private void initComponent() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
