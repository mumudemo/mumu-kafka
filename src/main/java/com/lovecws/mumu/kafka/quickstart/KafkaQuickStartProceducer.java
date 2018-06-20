package com.lovecws.mumu.kafka.quickstart;

import com.lovecws.mumu.kafka.KafkaConfiguration;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.ExecutionException;

public class KafkaQuickStartProceducer {

    /**
     * 发送同步消息
     *
     * @param message 消息
     * @param count   数量
     */
    public void sendMessage(String message, int count) {
        KafkaProducer<Integer, String> producer = KafkaConfiguration.kafkaProducer("KafkaQuickStartProceducer");

        try {
            for (int i = 0; i < count; i++) {
                Object o = producer.send(new ProducerRecord<Integer, String>(KafkaConfiguration.TOPIC, 0, i, message)).get();
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
     *
     * @param message 消息
     * @param count   数量
     */
    public void sendAsyncMessage(String message, int count) {
        KafkaProducer<Integer, String> producer = KafkaConfiguration.kafkaProducer("test-proceducer-group");

        for (int i = 0; i < count; i++) {
            Object o = producer.send(new ProducerRecord<Integer, String>(KafkaConfiguration.TOPIC, null, message), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println("message send end" + recordMetadata);
                }
            });
            System.out.println("send message:" + o);
        }
    }
}
