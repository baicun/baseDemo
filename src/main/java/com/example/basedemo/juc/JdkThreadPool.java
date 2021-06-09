package com.example.basedemo.juc;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : JdkThreadPool
 * @Description :
 * @Author : baicun
 * @Date:
 * @Version V1.0
 */
public class JdkThreadPool {
    public static void main(String[] args) {
        // 1、创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程
        // ExecutorService threadPool = Executors.newFixedThreadPool(3);
        // 2、这个线程池可以在线程死后（或发生异常时）重新启动一个线程来替代原来的线程继续执行下去！
        // ExecutorService threadPool = Executors.newSingleThreadExecutor();
        // 3、创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for(int i=0; i<10; i++){
            int task = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for(int j=0; j<10; j++){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + " the thread num:" + task + " the num is:" + j);
                    }
                }
            });
        }
        //threadPool.shutdown();//该状态下不再继续接受新提交的任务，但是还会处理阻塞队列中的任务；
        threadPool.shutdownNow(); //该状态下不再继续接受新提交的任务，同时不再处理阻塞队列中的任务；

        // 4、创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。
        Executors.newScheduledThreadPool(3).schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("");
            }
        }, 5,TimeUnit.SECONDS);
        // 固定频率的执行
        //Executors.newScheduledThreadPool().scheduleAtFixedRate();

    }
}
