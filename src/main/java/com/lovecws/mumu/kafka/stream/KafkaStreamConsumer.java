package com.lovecws.mumu.kafka.stream;

import com.lovecws.mumu.kafka.KafkaConfiguration;
import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * 获取流计算出来的结果
 */
public class KafkaStreamConsumer extends ShutdownableThread {

    private KafkaConsumer<String,Long> consumer;

    public KafkaStreamConsumer(){
        super("KafkaStreamConsumer",false);
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaStreamConsumer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaConfiguration.VALUE_DESERIALIZER_CLASS_CONFIG);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.LongDeserializer");

        consumer = new KafkaConsumer<String,Long>(props);
    }

    @Override
    public void doWork() {
        //consumer.subscribe(Collections.singleton("streams-wordcount-output"));
        consumer.subscribe(Collections.singleton("streams-wordcount-processor-output"));
        ConsumerRecords<String, Long> records = consumer.poll(1000);
        for (ConsumerRecord<String, Long> record : records) {
            System.out.println("Received message: (" +record);
        }
    }
}
