package com.example.basedemo.juc;

/**
 * @ClassName : MultiThreadShareDate
 * @Description : 多线程数据共享
 * @Author : baicun
 * @Date: 20210522
 * @Version V1.0
 */
public class MultiThreadShareDate {

    // 设计4个线程，其中两个线程每次对j增加1，另外两个线程对j每次减少1。

    private int j=0;

    public static void main(String[] args) {
        MultiThreadShareDate myshare = new MultiThreadShareDate();
        Inc inc = myshare.new Inc();
        Dec dec = myshare.new Dec();
        for(int i=0;i<2;i++){
            Thread incThread = new Thread(inc);
            incThread.start();
            /*try {
                incThread.join();
                System.out.println("line  = " + " ---");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            Thread decThread = new Thread(dec);
            decThread.start();

        }
    }

    class Inc implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 10; i++){
                inc();
            }
        }
    }

    class Dec implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 10; i++){
                dec();
            }
        }
    }

    private synchronized void inc(){
        j++;
        System.out.println(Thread.currentThread().getName() + " j inc ="+j);
    }

    private synchronized void dec(){
        j--;
        System.out.println(Thread.currentThread().getName() + " j dec ="+j);
    }
}
