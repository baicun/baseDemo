package com.example.basedemo.Loadbalance;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName : WeightRandom
 * @Description : 权重随机算法
 * @Author : baicun
 * @Date:
 * @Version V1.0
 */
public class WeightRandom {
    public static final int TOTAL_WEIGHT = 10;
    public static final Map<String, Integer> WEIGHT_IP = new LinkedHashMap<String, Integer>() {{
        put("127.0.0.1", 1);
        put("127.0.0.2", 3);
        put("127.0.0.3", 6);
    }};

    public String getServer() {
        java.util.Random random = new java.util.Random();
        int i = random.nextInt(TOTAL_WEIGHT);
        Iterator<Map.Entry<String, Integer>> iterator = WEIGHT_IP.entrySet().iterator();
        Map.Entry<String, Integer> entry = iterator.next();
        while (i >= entry.getValue()) {
            i -= entry.getValue();
            entry = iterator.next();
        }
        return entry.getKey();
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        WeightRandom weightRandom = new WeightRandom();
        for (int i = 0; i < 100000; i++) {
            String server = weightRandom.getServer();
            Integer count = map.getOrDefault(server, 0);
            map.put(server, count + 1);
        }
        System.out.println("随机结果量");
        map.forEach((k, v) -> System.out.printf("%s  %d\n", k, v));
    }
}

