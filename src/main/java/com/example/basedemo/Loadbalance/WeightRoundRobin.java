package com.example.basedemo.Loadbalance;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName : WeightRoundRobin
 * @Description : 加权轮询
 * @Author : baicun
 * @Version V1.0
 */
public class WeightRoundRobin {
    public static final int WEIGHT = 10;
    public static final Map<String, Integer> WEIGHT_IP = new LinkedHashMap<String, Integer>() {{
        put("127.0.0.1", 1);
        put("127.0.0.2", 3);
        put("127.0.0.3", 6);
    }};
    private int position;

    public String getServer() {
        int i = position++ % WEIGHT;
        Iterator<Map.Entry<String, Integer>> iterator = WEIGHT_IP.entrySet().iterator();
        Map.Entry<String, Integer> entry = iterator.next();
        while (i >= entry.getValue()) {
            i -= entry.getValue();
            entry = iterator.next();
        }
        return entry.getKey();
    }

    public static void main(String[] args) {
        WeightRoundRobin roundRobin = new WeightRoundRobin();
        for (int i = 0; i < 15; i++) {
            System.out.println(roundRobin.getServer());
        }
    }
}


