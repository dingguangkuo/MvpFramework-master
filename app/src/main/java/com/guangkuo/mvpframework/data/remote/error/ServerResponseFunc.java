package com.guangkuo.mvpframework.data.remote.error;

import com.guangkuo.mvpframework.data.bean.DataResponse;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 拦截固定格式的公共数据类型DataResponse<T>, 判断里面的状态码
 */
public class ServerResponseFunc<T> implements Function<DataResponse<T>, T> {
    @Override
    public T apply(@NonNull DataResponse<T> tResult) {
        // 对返回码进行判断
        if (tResult.getResult() != 200) {
            // 如果服务器端有错误信息返回，
            // 那么抛出异常，让下面的方法去捕获异常做统一处理
            throw new ServerException(tResult.getResult(), tResult.getMessage());
        }
        return tResult.getData();// 服务器请求数据成功，返回里面的数据实体
    }
}
