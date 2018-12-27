package com.guangkuo.mvpframework.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.guangkuo.mvpframework.BuildConfig;
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
        initARouter();// 初始化路由器ARoute
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

    /**
     * 初始化路由器ARoute
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();// 打印日志
            // 开启调试模式
            // (如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(this);// 尽可能早，推荐在Application中初始化
    }
}
