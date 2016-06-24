package com.niu.bigdata.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;

/**
 * Created by haisheng on 2016/6/24.
 */
public class SparkConnect {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("sparkTest");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        JavaRDD lines = jsc.textFile("hdfs://118.192.153.114:8020/aaa/aaa");
        long l = lines.count();
        List<String> list = lines.collect();
        System.out.println(l);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
