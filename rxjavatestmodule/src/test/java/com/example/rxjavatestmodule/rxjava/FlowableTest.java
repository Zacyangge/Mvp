package com.example.rxjavatestmodule.rxjava;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import static com.example.rxjavatestmodule.rxjava.PrintUtils.print;

/**
 * @author chenzaoyang
 * @date 2018/11/21 16:44
 */
public class FlowableTest {
    //Flowable的基础使用非常类似于 Observable  Rxjava2.0 ,解决Rxjava1中出现的对于异步订阅关系，存在 被观察者发送事件速度 与观察者接收事件速度 不匹配的情况
    @Test
    public void Flowable(){
        /**
         * 步骤1：创建被观察者 =  Flowable
         */
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);
        // 需要传入背压参数BackpressureStrategy，下面会详细讲解

        /**
         * 步骤2：创建观察者 =  Subscriber
         */
        Subscriber<Integer> downstream = new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription s) {
                // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
                // 相同点：Subscription具备Disposable参数的作用，即Disposable.dispose()切断连接, 同样的调用Subscription.cancel()切断连接
                // 不同点：Subscription增加了void request(long n)
                print("onSubscribe");
                s.request(Long.MAX_VALUE);
                // 关于request()下面会继续详细说明
            }

            @Override
            public void onNext(Integer integer) {
                print("onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                print("onError: "+ t);
            }

            @Override
            public void onComplete() {
                print("onComplete");
            }
        };

        /**
         * 步骤3：建立订阅关系
         */
        upstream.subscribe(downstream);
    }

    //更加优雅的链式调用
    @Test
    public void test1(){
        // 步骤1：创建被观察者 =  Flowable
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                print( "发送事件 1");
                emitter.onNext(1);
                print("发送事件 2");
                emitter.onNext(2);
                print( "发送事件 3");
                emitter.onNext(3);
                print("发送事件 4");
                emitter.onNext(4);
                print("发送完成");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    // 步骤2：创建观察者 =  Subscriber & 建立订阅关系

                    @Override
                    public void onSubscribe(Subscription s) {
                        print( "onSubscribe");
                        s.request(4);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        print("接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        print("onError: "+ t);
                    }

                    @Override
                    public void onComplete() {
                        print( "onComplete");
                    }
                });
    }
    //同步订阅，实时更新性，即一开始观察者要接收10个事件，发送了1个后，会实时更新为9个
    @Test
    public void test2(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                // 1. 调用emitter.requested()获取当前观察者需要接收的事件数量
                print( "观察者可接收事件数量 = " + emitter.requested());

                // 2. 每次发送事件后，emitter.requested()会实时更新观察者能接受的事件
                // 即一开始观察者要接收10个事件，发送了1个后，会实时更新为9个
                print( "发送了事件 1");
                emitter.onNext(1);
                print( "发送了事件1后, 还需要发送事件数量 = " + emitter.requested());

                print( "发送了事件 2");
                emitter.onNext(2);
                print( "发送事件2后, 还需要发送事件数量 = " + emitter.requested());

                print( "发送了事件 3");
                emitter.onNext(3);
                print( "发送事件3后, 还需要发送事件数量 = " + emitter.requested());

                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        print( "onSubscribe");

                        s.request(10); // 设置观察者每次能接受10个事件
                    }

                    @Override
                    public void onNext(Integer integer) {
                        print( "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        print( "onError: "+ t);
                    }

                    @Override
                    public void onComplete() {
                        print( "onComplete");
                    }
                });
    }

    //异步订阅，因为订阅者跟被订阅者不在同一个线程中，所以被观察者 无法通过 FlowableEmitter.requested()知道观察者自身接收事件能力，即 被观察者不能根据 观察者自身接收事件的能力 控制发送事件的速度
    @Test
    public void test3(){
    }
}
