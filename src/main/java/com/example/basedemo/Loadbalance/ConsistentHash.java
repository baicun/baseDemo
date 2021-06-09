package com.example.basedemo.Loadbalance;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @ClassName : ConsistentHash
 * @Description : 一致性哈希算法
 * @Author : baicun
 * @Version V1.0
 */
public class ConsistentHash {
    // 保存所有的虚拟服务器地址的 hashCode
    private TreeMap<Long, String> ipMap;
    private static final int VIRTUAL_NODE_NUMBER = 100;
    public static final List<String> IP_LIST = new ArrayList<String>() {{
        add("127.0.0.1");
        add("127.0.0.2");
        add("127.0.0.3");
    }};
    public ConsistentHash() {
        // 初始化虚拟地址
        ipMap = new TreeMap<>();
        for (String ip : IP_LIST) {
            for (int i = 0; i < VIRTUAL_NODE_NUMBER; i++) {
                ipMap.put(hash(ip + "V" + i), ip);
            }
        }
    }

    public String getServer(String clientIp) {
        // 计算客户端 ip 的 hashCode
        long hash = hash(clientIp);
        // 默认取第一个虚拟节点
        Long ipKey = ipMap.firstKey();
        // 由于 TreeMap 是根据 key 排序的，所以可以获取到大于 client hashCode 的集合
        SortedMap<Long, String> tailMap = ipMap.tailMap(hash);
        // 如果这个集合有元素，则取这个集合里的第一个元素
        if (!tailMap.isEmpty()) {
            ipKey = tailMap.firstKey();
        }
        return ipMap.get(ipKey);
    }

    public static void main(String[] args) {
        ConsistentHash consistentHash = new ConsistentHash();
        for (int i = 0; i < 5; i++) {
            System.out.println(consistentHash.getServer("www.111.com"));
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(consistentHash.getServer("www.222.com"));
        }
    }

    public long hash(String value) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes;
        keyBytes = value.getBytes(StandardCharsets.UTF_8);
        md5.update(keyBytes);
        byte[] digest = md5.digest();
        long hashCode = ((long) (digest[3] & 0xFF) << 24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);

        return hashCode & 0xffffffffL;
    }
}

