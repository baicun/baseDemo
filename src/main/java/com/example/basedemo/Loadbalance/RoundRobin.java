package com.example.basedemo.Loadbalance;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : RoundRobin
 * @Description : 普通轮询
 * @Author : baicun
 * @Date:
 * @Version V1.0
 */
public class RoundRobin {
    // 当前需要返回的 ip
    private int position;
    public static final List<String> IP_LIST = new ArrayList<String>() {{
        add("127.0.0.1");
        add("127.0.0.2");
        add("127.0.0.3");
    }};

    public String getServer() {
        return IP_LIST.get(position++ % IP_LIST.size());
    }

    public static void main(String[] args) {
        RoundRobin roundRobin = new RoundRobin();
        for (int i = 0; i < 10; i++) {
            System.out.println(roundRobin.getServer());
        }
    }
}

