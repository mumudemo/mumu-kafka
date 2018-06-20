package com.lovecws.mumu.kafka.partition;

import com.lovecws.mumu.kafka.KafkaConfiguration;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class UFNProceducer {

    public static KafkaProducer kafkaProducer(String clientId) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);//重试次数
        props.put(ProducerConfig.ACKS_CONFIG, "1");//ack数量 0：不需要确认，1：只需要主节点确认接受成功，2：all:主节点和副本都接受消息成功
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");//默认不压缩
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.lovecws.mumu.kafka.partition.UFNPartition");//自定义分区器
        KafkaProducer<Integer, String> producer = new KafkaProducer<Integer, String>(props);
        return producer;
    }

    /**
     * 发送同步消息
     *
     * @param message 消息
     * @param count   数量
     */
    public void sendMessage(String message, int count) {
        KafkaProducer<Integer, String> producer = UFNProceducer.kafkaProducer("UFNProceducer");
        try {
            for (int i = 0; i < count; i++) {
                Object o = producer.send(new ProducerRecord<Integer, String>(KafkaConfiguration.TOPIC, i, message)).get();
                System.out.println("send message:" + o);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
