package com.guangkuo.mvpframework.module.user;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.guangkuo.mvpframework.R;
import com.guangkuo.mvpframework.base.BaseActivity;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

@SuppressLint("CheckResult")
@Route(path = "/activity/LoginActivity")
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.editUsername)
    EditText mEditUsername;
    @BindView(R.id.editPassword)
    EditText mEditPassword;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void bindListener() {
        RxView.clicks(mBtnLogin)
                .compose(this.bindToLife())
                .debounce(2, TimeUnit.SECONDS)// 两秒钟之内只取一个点击事件，防抖操作
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) {
                        if (mPresenter != null) {
                            mPresenter.login(mEditUsername.getText().toString().trim(),
                                    mEditPassword.getText().toString().trim());
                        }
                    }
                });
        textChanges(mEditUsername);
        textChanges(mEditPassword);
    }

    /**
     * 输入框内容改变监听
     *
     * @param view
     */
    private void textChanges(TextView view) {
        RxTextView.textChanges(view).compose(this.<CharSequence>bindToLife())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) {
                        String username = mEditUsername.getText().toString().trim();
                        String password = mEditPassword.getText().toString().trim();
                        mBtnLogin.setEnabled(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password));
                    }
                });
    }

    @Override
    public void onLoadSuccess(Object result) {
        ARouter.getInstance().build("/activity/MainActivity").navigation();
        finish();
    }
}
