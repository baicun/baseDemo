package com.example.basedemo.juc;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : MyThreadLocalNoRemove
 * @Description :  使用ThreadLocal，但不remove
 * @Author : baicun
 * @Date:
 * @Version V1.0
 */
public class MyThreadLocalNoRemove {
    public static final Integer SIZE = 500;
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 5, 1,
            TimeUnit.MINUTES, new LinkedBlockingDeque<>());

    static class LocalVariable {//总共有5M
        private byte[] locla = new byte[1024 * 1024 * 5];
    }
    static ThreadLocal<LocalVariable> local = new ThreadLocal<>();

    public static void main(String[] args) {
        try {
            for (int i = 0; i < SIZE; i++) {
                executor.execute(() -> {
                    local.set(new LocalVariable());
                    System.out.println("开始执行:"+local.get().toString());
                });
                Thread.sleep(100);
            }
            local = null;//这里设置为null，依旧会造成内存泄漏
        } catch (InterruptedException e) {
            System.out.println("异常 = [" + e + "]");
        }
    }
}
