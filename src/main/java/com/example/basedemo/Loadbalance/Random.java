package com.example.basedemo.Loadbalance;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : Random
 * @Description : 随机算法
 * @Author : baicun
 * @Version V1.0
 */
public class Random {
    public static final List<String> IP_LIST = new ArrayList<String>() {{
        add("127.0.0.1");
        add("127.0.0.2");
        add("127.0.0.3");
    }};

    public String getServer() {
        java.util.Random random = new java.util.Random();
        return IP_LIST.get(random.nextInt(IP_LIST.size()));
    }

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.getServer());
        }
    }
}

