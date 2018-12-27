package com.guangkuo.mvpframework.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulers {
    private static final ObservableTransformer TRANSFORMER = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable observable) {
            return observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };


    public static <T> ObservableTransformer<T, T> applySchedulers() {
        // noinspection unchecked
        return (ObservableTransformer<T, T>) TRANSFORMER;
    }
}
