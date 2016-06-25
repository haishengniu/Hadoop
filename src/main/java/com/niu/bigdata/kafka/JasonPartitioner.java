package com.niu.bigdata.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Created by haisheng on 2016/6/25.
 */
public class JasonPartitioner implements Partitioner{

    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        try {
            int partitionNum = Integer.parseInt((String) o);
            return Math.abs(Integer.parseInt((String) o));  //取余
        } catch (Exception e) {
            return Math.abs(o.hashCode());
        }
    }

    public void close() {

    }

    public void configure(Map<String, ?> map) {

    }
}
