package com.lovecws.mumu.kafka.interceptor;

import com.lovecws.mumu.kafka.KafkaConfiguration;
import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class KafkaInterceptorConsumer extends ShutdownableThread {

    private KafkaConsumer<Integer,String> consumer;

    public KafkaInterceptorConsumer(){
        super("KafkaInterceptorConsumer",false);
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaConfiguration.KEY_DESERIALIZER_CLASS_CONFIG);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaConfiguration.VALUE_DESERIALIZER_CLASS_CONFIG);

        //消息拦截器
        props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG,"com.lovecws.mumu.kafka.interceptor.KafkaInterceptor");

        consumer = new KafkaConsumer<Integer,String>(props);
    }

    @Override
    public void doWork() {
        consumer.subscribe(Collections.singleton(KafkaConfiguration.TOPIC));
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        for (ConsumerRecord<Integer, String> record : records) {
            System.out.println("Received message: " +record);
        }
    }
}
