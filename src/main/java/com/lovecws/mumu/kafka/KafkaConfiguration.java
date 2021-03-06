package com.lovecws.mumu.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class KafkaConfiguration {

    public static String BOOTSTRAP_SERVERS_CONFIG=null;

    public static final String KEY_DESERIALIZER_CLASS_CONFIG="org.apache.kafka.common.serialization.IntegerDeserializer";
    public static final String VALUE_DESERIALIZER_CLASS_CONFIG="org.apache.kafka.common.serialization.StringDeserializer";

    public static final String TOPIC="babymm";

    static {
        //从环境变量中 获取
        String KAFKASERVICES = System.getenv("KAFKASERVICES");
        if(KAFKASERVICES!=null){
            BOOTSTRAP_SERVERS_CONFIG=KAFKASERVICES;
        }else{
            BOOTSTRAP_SERVERS_CONFIG="192.168.11.25:9092";
        }
    }

    public static KafkaProducer kafkaProducer(String clientId) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);//重试次数
        props.put(ProducerConfig.ACKS_CONFIG, "1");//ack数量 0：不需要确认，1：只需要主节点确认接受成功，2：all:主节点和副本都接受消息成功
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");//默认不压缩
        KafkaProducer<Integer, String> producer = new KafkaProducer<Integer, String>(props);
        return producer;
    }
}
