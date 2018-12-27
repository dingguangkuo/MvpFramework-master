package com.guangkuo.mvpframework.di.components;

import android.content.Context;

import com.guangkuo.mvpframework.di.scopes.ContextLife;
import com.guangkuo.mvpframework.di.modules.AppModule;
import com.guangkuo.mvpframework.di.scopes.PerApp;

import dagger.Component;

@PerApp
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife
    Context getApplication();
}
