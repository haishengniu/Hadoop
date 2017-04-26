package com.niu.bigdata.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

/**
 * Created by haisheng on 2017/4/26.
 */
public class WordCountTest {
    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Job job = Job.getInstance(conf);
        job.setJobName("wordcount");
        job.setJarByClass(WordCountTest.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);
        job.setNumReduceTasks(1);
        FileInputFormat.addInputPath(job,new Path(args[0]));
        Path outputPath = new Path(args[1]);
        if (fs.exists(outputPath)) {
            fs.delete(outputPath,true);
        }
        FileOutputFormat.setOutputPath(job,outputPath);
        boolean f = job.waitForCompletion(true);
        if(f) {
            System.out.println("任务执行完成");
        }

    }
}

class WCMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] words = StringUtils.split(value.toString(),',');
        for(String w : words) {
            context.write(new Text(w),new IntWritable(1));
        }
    }
}

class WCReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable value : values) {
            sum = sum + value.get();
        }
        context.write(key,new IntWritable(sum));
    }
}




