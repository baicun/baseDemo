package com.example.basedemo.juc;


/**
 * @ClassName : MyThreadLocalShare
 * @Description :
 * @Author : baicun
 * @Date:
 * @Version V1.0
 */
public class MyThreadLocalShare {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                print1();
            }
        }).start();

        print2();
        print3();
    }

    public static void print1(){
       System.out.println(Thread.currentThread().getName() + "-111");
    }
    public static void print2(){
        System.out.println(Thread.currentThread().getName() + "-222");
    }
    public static void print3(){
        System.out.println(Thread.currentThread().getName() + "-333");
    }
}
