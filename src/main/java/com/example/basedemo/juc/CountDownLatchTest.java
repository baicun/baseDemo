package com.example.basedemo.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName : CountDownLatchTest
 * @Author : baicun
 * @Version V1.0
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CountDownLatch order = new CountDownLatch(1);
        CountDownLatch answer = new CountDownLatch(3);
        for(int i=0;i<3;i++){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程"+Thread.currentThread().getName() + "等待接收命令");
                        order.await();

                        System.out.println("线程"+Thread.currentThread().getName() + "已经接收命令");
                        Thread.sleep(2000);
                        System.out.println("线程"+Thread.currentThread().getName() + "回应处理结果");
                        answer.countDown();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            threadPool.execute(runnable);
        }

        try {
            Thread.sleep(5000);
            System.out.println("线程"+Thread.currentThread().getName() + "发布命令");
            order.countDown();

            answer.await();
            System.out.println("线程"+Thread.currentThread().getName() + "完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
