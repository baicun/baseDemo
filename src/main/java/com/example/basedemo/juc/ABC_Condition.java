package com.example.basedemo.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName : ABC_Condition
 * @Description : condition控制线程顺序
 * @Author : baicun
 * @Date: 20210523
 * @Version V1.0
 */
public class ABC_Condition {
    private static int count = 0;
    private static Lock lock = new ReentrantLock();
    private static Condition acondition = lock.newCondition();
    private static Condition bcondition = lock.newCondition();
    private static Condition ccondition = lock.newCondition();

    public static void main(String[] args) {
        new printA().start();
        new printB().start();
        new printC().start();
    }


    static class printA extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 0)
                        acondition.await();// A释放lock锁
                    System.out.println("A");
                    count++;
                    bcondition.signal();// A执行完唤醒B线程

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class printB extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 1)
                        bcondition.await();
                    System.out.println("B");
                    count++;
                    ccondition.signal();// B执行完唤醒C线程
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class printC extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 2)
                        ccondition.await();
                    System.out.println("C");
                    count++;
                    acondition.signal();// C执行完唤醒A线程
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
