package com.guangkuo.mvpframework.di.components;

import android.app.Activity;
import android.content.Context;

import com.guangkuo.mvpframework.di.scopes.ContextLife;
import com.guangkuo.mvpframework.di.modules.FragmentModule;
import com.guangkuo.mvpframework.di.scopes.PerFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getAcitivtyContext();

    @ContextLife
    Context getApplicationContext();

    Activity getAcitivty();

    //    void inject(UndoneFragment fragment);
    //
    //    void inject(DoneFragment fragment);
}
