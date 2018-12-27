package com.guangkuo.mvpframework.di.components;

import android.app.Activity;
import android.content.Context;

import com.guangkuo.mvpframework.di.scopes.ContextLife;
import com.guangkuo.mvpframework.di.modules.ActivityModule;
import com.guangkuo.mvpframework.di.scopes.PerActivity;
import com.guangkuo.mvpframework.module.user.LoginActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife
    Context getApplicationContext();

    Activity getActivity();

    // 这个部分可以先不写，未来需要注入哪个activity写下就可以了
    void inject(LoginActivity activity);
    // void inject(RegisterActivity activity);
}