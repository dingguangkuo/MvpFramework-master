package com.guangkuo.mvpframework.data.remote.error;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.JsonParseException;
import com.guangkuo.mvpframework.R;
import com.guangkuo.mvpframework.app.App;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import retrofit2.HttpException;

public class ExceptionEngine {
    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            // HTTP错误（401/403/404/408/500/502等）
            HttpException httpException = (HttpException) e;
            LogUtils.e("HttpException code = " + httpException.code());
            ex = new ApiException(e, ErrorCode.HTTP_ERROR);
            ex.message = App.getInstance().getString(R.string.error_network);
            return ex;
        } else if (e instanceof ServerException) {
            // 服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.result);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            // 均视为解析错误
            ex = new ApiException(e, ErrorCode.PARSE_ERROR);
            ex.message = App.getInstance().getString(R.string.error_parse);
            return ex;
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
            // 均视为网络错误
            ex = new ApiException(e, ErrorCode.NETWORK_ERROR);
            ex.message = App.getInstance().getString(R.string.error_connection);
            return ex;
        } else {
            // 未知错误
            e.printStackTrace();
            ex = new ApiException(e, ErrorCode.UNKNOWN);
            ex.message = App.getInstance().getString(R.string.error_unknown);
            return ex;
        }
    }
}