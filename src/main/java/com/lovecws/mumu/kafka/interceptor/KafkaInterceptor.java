package com.lovecws.mumu.kafka.interceptor;

import com.alibaba.fastjson.JSON;
import com.lovecws.mumu.kafka.KafkaConfiguration;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * 消息拦截器
 */
public class KafkaInterceptor implements ConsumerInterceptor {

    private TopicPartition topicPartition=null;

    @Override
    public ConsumerRecords onConsume(ConsumerRecords consumerRecords) {
        List<ConsumerRecord<Integer,String>> consumerRecordList=new ArrayList<ConsumerRecord<Integer, String>>();
        topicPartition = new TopicPartition(KafkaConfiguration.TOPIC, 0);
        //获取主题下的0分区下的消息
        List<ConsumerRecord<Integer,String>> records = consumerRecords.records(topicPartition);
        Iterator iterator = records.iterator();
        while (iterator.hasNext()){
            ConsumerRecord<Integer,String> consumerRecord = (ConsumerRecord<Integer, String>) iterator.next();
            Integer key = consumerRecord.key();
            //将key大于10的记录过滤掉
            if(key>10){
                consumerRecordList.add(consumerRecord);
            }else{
                //TODO 过滤掉的消息直接丢弃吗？
            }
        }
        Map<TopicPartition, List<ConsumerRecord<Integer, String>>> recordMap=new HashMap<TopicPartition, List<ConsumerRecord<Integer, String>>>();
        recordMap.put(topicPartition,consumerRecordList);
        return new ConsumerRecords(recordMap);
    }

    @Override
    public void onCommit(Map map) {
        OffsetAndMetadata metadata = (OffsetAndMetadata) map.get(topicPartition);

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {
        System.out.println("configure:"+map);
    }
}
