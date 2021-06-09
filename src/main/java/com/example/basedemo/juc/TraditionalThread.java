package com.example.basedemo.juc;

/**
 * @ClassName : TraditionalThread
 * @Description :线程创建
 * @Author : baicun
 * @Date:
 * @Version V1.0
 */
public class TraditionalThread {
    public static void main(String[] args) {
        Thread thread = new Thread(){
            @Override
            public void run(){
                while (true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("--:"+Thread.currentThread().getName());
                }
            }
        };
        thread.start();



        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("--:"+Thread.currentThread().getName());
            }
        });
        thread2.start();


        // 问题,Thread既继承了Runnable,又重写了子类，程序执行会执行那部分代码？
        //new Thread(new Runnable() {public void run() {}){public void run() {}}.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Runnable:"+Thread.currentThread().getName());
                }
            }
        }){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread:"+Thread.currentThread().getName());
                }
            }
        }.start();
        // 答案：子类方法代码，及打印“thread:Thread-0”
    }
}
