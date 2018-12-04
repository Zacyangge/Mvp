package com.example.rxjavatestmodule.rxjava;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.example.rxjavatestmodule.rxjava.PrintUtils.print;

/**
 * @author ligq
 * @date 2018/10/17
 */
public class RxJavaTest {
    @Test
    public void test() {
        Observable.just(1).compose(new ObservableTransformer<Integer, String>() {
            @Override
            public ObservableSource<String> apply(Observable<Integer> upstream) {
                return upstream.flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return Observable.just("abc" + integer);
                    }
                });
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                print(s);
            }
        });
    }
}
