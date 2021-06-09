package com.example.basedemo.juc;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : MyThreadLocal
 * @Description : 查看堆内存释放情况
 * @Author : baicun
 * @Date:
 * @Version V1.0
 */
public class MyThreadLocal {
    public static final Integer SIZE = 500;
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 5, 1,
            TimeUnit.MINUTES, new LinkedBlockingDeque<>());

    static class LocalVariable {//总共有5M
        private byte[] locla = new byte[1024 * 1024 * 5];
    }
    public static void main(String[] args) {
        try {
            for (int i = 0; i < SIZE; i++) {
                executor.execute(() -> {
                    new LocalVariable();
                    System.out.println("开始执行");
                });
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
