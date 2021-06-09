package com.example.basedemo.juc;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @ClassName : CallableAndFuture
 * @Author : baicun
 * @Version V1.0
 */
public class CallableAndFuture {

    public static void main(String[] args) {
        // 单独线程获得执行结果
        ExecutorService threadpool = Executors.newSingleThreadExecutor();
        // 实现Callable接口返回泛型对象
        Future future = threadpool.submit(new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(new Random().nextInt(5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("args = [" + "-----" + "]");
                return "hello";
            }
        });
        System.out.println("args = [" + "等待结果" + "]");
        //future.cancel(false); // 取消提交的任务
        try {
            System.out.println("args = [" + future.get() + "]");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 线程池获得执行结果
        ExecutorService threadPools = Executors.newFixedThreadPool(10);
        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(threadPools);
        for(int i=0;i<10;i++){
            final int seq = i;
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    try {
                        Thread.sleep(new Random().nextInt(5000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return seq;
                }
            });
        }
        // 获取结果
        for(int i=0;i<10;i++){
            try {
                Integer integer = completionService.take().get();
                System.out.println("args = [" + integer + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
