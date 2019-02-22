package com.guangkuo.mvpframework.data.remote;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.guangkuo.mvpframework.BuildConfig;
import com.guangkuo.mvpframework.app.App;
import com.guangkuo.mvpframework.app.AppConfig;
import com.guangkuo.mvpframework.data.remote.interceptor.RewriteCacheControlInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private final static long CONNECT_TIMEOUT = 60L;
    private final static long READ_TIMEOUT = 10L;
    private final static long WRITE_TIMEOUT = 10L;

    private static volatile OkHttpClient mOkHttpClient;
    // 云端响应头拦截器，用来配置缓存策略
    private static final Interceptor mRewriteCacheControlInterceptor = new RewriteCacheControlInterceptor();

    /**
     * 获取OkHttpClient实例
     *
     * @return OkHttpClient实例
     */
    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                ClearableCookieJar cookieJar = new PersistentCookieJar(
                        new SetCookieCache(), new SharedPrefsCookiePersistor(App.getInstance()));
                Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                if (mOkHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .cache(cache)
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .cookieJar(cookieJar);
                    if (BuildConfig.DEBUG) {
                        // Log信息拦截器
                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        // 设置 Debug Log 模式
                        builder.addInterceptor(loggingInterceptor);
                    }
                    mOkHttpClient = builder.build();
                }
            }
        }
        return mOkHttpClient;
    }

    /**
     * 获取Service
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AppConfig.REQUEST_BASE_URL)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
