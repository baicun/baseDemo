package com.example.basedemo.juc;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : MyThreadLocalRemove
 * @Description : 线程致性完成后，都调用了local.remove()来将threadLocal内的对象删除
 * @Author : baicun
 * @Version V1.0
 */
public class MyThreadLocalRemove {
    public static final Integer SIZE = 500;
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 5, 1,
            TimeUnit.MINUTES, new LinkedBlockingDeque<>());

    static class LocalVariable {//总共有5M
        private byte[] locla = new byte[1024 * 1024 * 5];
    }

    final static ThreadLocal<LocalVariable> local = new ThreadLocal<>();
    public static void main(String[] args) {
        try {
            for (int i = 0; i < SIZE; i++) {
                executor.execute(() -> {
                    local.set(new LocalVariable());
                    System.out.println("开始执行");
                    local.remove();
                });
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
