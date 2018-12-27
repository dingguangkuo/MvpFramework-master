package com.guangkuo.mvpframework.data.remote.api;

import com.guangkuo.mvpframework.data.bean.DataResponse;
import com.guangkuo.mvpframework.data.bean.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * ApiService
 * <p>
 * Created by Guangkuo on 2018/12/25.
 */
public interface ApiService {
    /**
     * 登录接口
     * http://www.wanandroid.com/user/login
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @POST("/user/login")
    @FormUrlEncoded
    Observable<DataResponse<User>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 注册用户的方法
     * http://www.wanandroid.com/user/register
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     * @return
     */
    @POST("/user/register")
    @FormUrlEncoded
    Observable<DataResponse> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);
}
