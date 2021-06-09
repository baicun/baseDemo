package com.example.basedemo.juc;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName : LockTest
 * @Author : baicun
 * @Version V1.0
 */
public class CacheReadWriteLock {
    Map cache = new HashMap<>();
    ReadWriteLock rwl = new ReentrantReadWriteLock();
    public static void main(String[] args) {
        String value = new CacheReadWriteLock().getData("5");
        System.out.println("获取缓存结果 = [" + value + "]");
    }

    public String getData(String key){
        rwl.readLock().lock();
        String msg = "";
        if(key==null){
            return "";
        }
        try {
            msg = (String) cache.get(key);
            if(msg==null){
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try{
                    if(msg==null){
                        // 执行查询操作
                        for(int i=0;i<10;i++){
                            cache.put(String.valueOf(i),new Random().nextInt(10));
                        }
                    }
                } finally {
                    rwl.writeLock().unlock();
                }
                rwl.readLock().lock();
            }
            msg = String.valueOf(cache.get(key));
        } finally {
            rwl.readLock().unlock();
        }
        return msg;
    }
}
