package com.lovecws.mumu.kafka.stream;

import com.lovecws.mumu.kafka.KafkaConfiguration;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaStreamProceducer {

    /**
     * 向kafka stream流中发送消息
     * @param message 消息
     * @param count 数量
     */
    public void sendMessage(String message,int count) {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put("client.id", "KafkaStreamProceducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("retries", 3);//重试次数
        KafkaProducer<Integer,String> producer = new KafkaProducer<Integer,String>(props);

        try {
            for (int i = 0; i < count; i++) {
                Object o = producer.send(new ProducerRecord<Integer, String>("streams-file-input", 0,i, message)).get();
                System.out.println("send message:" + o);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
