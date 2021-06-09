package com.example.basedemo.Loadbalance;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : SmoothingWeightRoundRobin
 * @Description : 平滑加权轮循
 * @Author : baicun
 * @Version 加权轮询存在一个分布不均匀的问题，短时间大量请求都落在同一台服务器上去了，所以平滑加权轮询可以在加权轮询的基础上，实现请求平滑过渡。
 */
public class SmoothingWeightRoundRobin {

    public static final int WEIGHT = 10;
    public static final Map<String, Integer> WEIGHT_IP = new LinkedHashMap<String, Integer>() {{
        put("127.0.0.1", 1);
        put("127.0.0.2", 3);
        put("127.0.0.3", 6);
    }};

    private List<Weight> weightList;

    public SmoothingWeightRoundRobin() {
        weightList = new ArrayList<>(WEIGHT_IP.size());
        Weight weight;
        for (Map.Entry<String, Integer> entry : WEIGHT_IP.entrySet()) {
            weight = new Weight(entry.getValue(), 0, entry.getKey());
            weightList.add(weight);
        }
    }

    public String getServer() {
        // 当前权重最大的ip
        Weight maxWeight = weightList.get(0);
        // 处理 currentWeight，currentWeight = currentWeight+weight
        for (Weight weight : weightList) {
            weight.currentWeight += weight.weight;
            if (maxWeight.currentWeight < weight.currentWeight) {
                maxWeight = weight;
            }
        }
        // 将返回的ip的currentWeight - 总权重
        maxWeight.currentWeight -= WEIGHT;
        return maxWeight.ipAddress;
    }

    static class Weight {
        int weight;
        int currentWeight;
        String ipAddress;

        public Weight(int weight, int currentWeight, String ipAddress) {
            this.weight = weight;
            this.currentWeight = currentWeight;
            this.ipAddress = ipAddress;
        }
    }

    public static void main(String[] args) {
        SmoothingWeightRoundRobin roundRobin = new SmoothingWeightRoundRobin();
        for (int i = 0; i < 20; i++) {
            System.out.println(roundRobin.getServer());
        }
    }
}

