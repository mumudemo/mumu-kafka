package com.lovecws.mumu.kafka.quickstart;

import com.lovecws.mumu.kafka.KafkaConfiguration;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaQuickStartProceducer {

    /**
     * 发送同步消息
     * @param message 消息
     * @param count 数量
     */
    public void sendMessage(String message,int count) {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put("client.id", "KafkaQuickStartProceducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("retries", 3);//重试次数
        KafkaProducer<Integer,String> producer = new KafkaProducer<Integer,String>(props);

        try {
            for (int i = 0; i < count; i++) {
                Object o = producer.send(new ProducerRecord<Integer, String>(KafkaConfiguration.TOPIC, 0,i, message)).get();
                System.out.println("send message:" + o);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送异步消息
     * @param message 消息
     * @param count 数量
     */
    public void sendAsyncMessage(String message,int count) {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put("client.id", "test-proceducer-group");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<Integer,String> producer = new KafkaProducer<Integer,String>(props);

        try {
            for (int i = 0; i < count; i++) {
                Object o = producer.send(new ProducerRecord<Integer, String>(KafkaConfiguration.TOPIC, null, message), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        System.out.println("message send end"+recordMetadata);
                    }
                }).get();
                System.out.println("send message:" + o);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
