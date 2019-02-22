package com.guangkuo.mvpframework.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.guangkuo.mvpframework.R;
import com.guangkuo.mvpframework.app.App;
import com.guangkuo.mvpframework.data.remote.error.ApiException;
import com.guangkuo.mvpframework.di.components.ActivityComponent;
import com.guangkuo.mvpframework.di.components.DaggerActivityComponent;
import com.guangkuo.mvpframework.di.modules.ActivityModule;
import com.guangkuo.mvpframework.module.user.LoginActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseActivity
 *
 * @param <P> Presenter
 */
public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements BaseContract.BaseView {
    @Nullable
    @Inject
    protected P mPresenter;
    protected ActivityComponent mActivityComponent;
    @Nullable
    protected Toolbar mToolbar;
    private Unbinder unbinder;

    /**
     * 是否显示返回键
     *
     * @return
     */
    protected boolean showHomeAsUp() {
        return false;
    }

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView();

    protected abstract void bindListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComponent();
        setContentView(getLayoutId());
        initInjector();
        unbinder = ButterKnife.bind(this);
        initToolBar();
        attachView();
        initView();
        bindListener();
        if (!NetworkUtils.isConnected()) {
            showNoNet();
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void stopLoading() {
    }

    @Override
    public void onLoadFailed(ApiException ex) {
        ToastUtils.showLong(ex.getMessage());
    }

    @Override
    public void onLoadSuccess(Object result) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        detachView();
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 跳转到登录页面
     */
    @Override
    public void jumpToLogin() {
        ActivityUtils.startActivity(LoginActivity.class);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    public void showNoNet() {
        ToastUtils.showShort(R.string.no_network_connection);
    }

    protected void setToolbarTitle(String title) {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(title);
        }
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 初始化toolbar
     */
    protected void initToolBar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new NullPointerException("toolbar can not be null");
        }
        setSupportActionBar(mToolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(showHomeAsUp());
            // toolbar除掉阴影
            bar.setElevation(0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setElevation(0);
        }
    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent(((App) getApplication()).getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
}
