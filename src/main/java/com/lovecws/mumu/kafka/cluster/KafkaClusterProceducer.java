package com.lovecws.mumu.kafka.cluster;

import com.lovecws.mumu.kafka.KafkaConfiguration;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * kafka集群发送消息
 */
public class KafkaClusterProceducer {

    public void sendClusterMessage(){
        Properties props = new Properties();
        //props.put("bootstrap.servers", "192.168.0.22:9092,192.168.0.22:9093,192.168.0.22:9094");
        props.put("bootstrap.servers", KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put("client.id", "KafkaClusterProceducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<Integer,String> producer = new KafkaProducer<Integer,String>(props);

        try {
            Object o = producer.send(new ProducerRecord<Integer, String>(KafkaConfiguration.TOPIC, 0,0, "lovecws")).get();
            System.out.println("send message:" + o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
