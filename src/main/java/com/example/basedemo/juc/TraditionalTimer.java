package com.example.basedemo.juc;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName : TraditionalTimer
 * @Description : 定时器
 * @Author : baicun
 * @Date:
 * @Version V1.0
 */
public class TraditionalTimer {
    // 隔X秒打印：bombing
    public static void main2(String[] args) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("bombing:");
            }
        },1000, 3000);
        while(true){
            System.out.println("args = [" + new Date().getSeconds() + "]");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // 第1次bombing是2秒，第二次是4秒，第三次是2秒，第四次是4秒....
    private static int count = 0;
    public static void main(String[] args) {
        System.out.println("args1 = [" + new Date().getSeconds() + "]");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("bombing");
                new Timer().schedule(new MyTimerTask(),2000);
            }
        },2000);
    }
    static class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            count = (count+1)%2;
            System.out.println("bombing");
            new Timer().schedule(new MyTimerTask(),2000+2000*count);
        };
    }
}
