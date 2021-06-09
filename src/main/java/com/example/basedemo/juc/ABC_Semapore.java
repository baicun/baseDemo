package com.example.basedemo.juc;

import java.util.concurrent.Semaphore;

/**
 * @ClassName : ABC_Semapore
 * @Description :
 * @Author : baicun
 * @Date:
 * @Version V1.0
 */
public class ABC_Semapore {
    // 以A开始的信号量,初始信号量数量为1
    private static Semaphore semaphoreA= new Semaphore(1);
    // B、C信号量,A完成后开始,初始信号数量为0
    private static Semaphore semaphoreB= new Semaphore(0);
    private static Semaphore semaphoreC= new Semaphore(0);
    public static void main(String[] args) {
        new PrintA().start();
        new PrintB().start();
        new PrintC().start();
        // 初始(A=1,B=0,C=0)
        // —>第一次执行线程A时(A=1,B=0,C=0)
        // ->第一次执行线程B时（A=0,B=1,C=0）
        // ->第一次执行线程C时(A=0,B=0,C=1)
        // —>第二次执行线程A(A=1,B=0,C=0)如此循环。
    }

    static class PrintA extends Thread{
        @Override
        public void run() {
            try {
                for(int i=0;i<10;i++){
                    semaphoreA.acquire();
                    System.out.println("A");
                    semaphoreB.release();
                    //System.out.println("A:"+semaphoreA.availablePermits() + " B:"+ semaphoreB.availablePermits() +" C:"+semaphoreC.availablePermits());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class PrintB extends Thread{
        @Override
        public void run() {
            try {
                for(int i=0;i<10;i++){
                    semaphoreB.acquire();
                    System.out.println("B");
                    semaphoreC.release();
                    //System.out.println("A:"+semaphoreA.availablePermits() + " B:"+ semaphoreB.availablePermits() +" C:"+semaphoreC.availablePermits());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class PrintC extends Thread{
        @Override
        public void run() {
            try {
                for(int i=0;i<10;i++){
                    semaphoreC.acquire();
                    System.out.println("C");
                    semaphoreA.release();
                    //System.out.println("A:"+semaphoreA.availablePermits() + " B:"+ semaphoreB.availablePermits() +" C:"+semaphoreC.availablePermits());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
