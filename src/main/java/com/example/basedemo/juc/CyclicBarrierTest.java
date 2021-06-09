package com.example.basedemo.juc;

import java.util.concurrent.CyclicBarrier;

/**
 * @ClassName : CyclicBarrierTest
 * @Description :
 * @Author : baicun
 * @Date:
 * @Version V1.0
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        int threadNum = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 完成最后任务");
            }
        });
        for(int i = 0; i < threadNum; i++) {
            new TaskThread(cyclicBarrier).start();
        }
    }

    static class TaskThread extends Thread{
        private final CyclicBarrier cyclicBarrier;
        public TaskThread(CyclicBarrier cyclicBarrier){
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(getName() + " 到达栅栏 A");
                cyclicBarrier.await();
                System.out.println(getName() + " 冲破栅栏 A");

                Thread.sleep(2000);
                System.out.println(getName() + " 到达栅栏 B");
                cyclicBarrier.await();
                System.out.println(getName() + " 冲破栅栏 B");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
