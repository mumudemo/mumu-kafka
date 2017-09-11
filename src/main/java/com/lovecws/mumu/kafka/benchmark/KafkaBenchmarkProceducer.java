package com.lovecws.mumu.kafka.benchmark;

import com.lovecws.mumu.kafka.KafkaConfiguration;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 使用jmh测试kafka并发量
 */
public class KafkaBenchmarkProceducer {

    private static KafkaProducer<Integer,String> producer=null;
    private static byte[] KAFKA_MESSAGE=new byte[10];
    static {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put("client.id", "KafkaBenchmarkProceducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<Integer,String>(props);
    }

    /**
     * 并发测试同步发送消息,消息发送完毕，不等待消息发送结果
     */
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void sendMessage(){
        producer.send(new ProducerRecord<Integer, String>(KafkaConfiguration.TOPIC,null, new String(KAFKA_MESSAGE)));
    }

    /**
     * 并发测试同步发送消息，消息发送完毕，等待消息发送结果
     */
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void sendMessageWithResult(){
        try {
            producer.send(new ProducerRecord<Integer, String>(KafkaConfiguration.TOPIC,null, new String(KAFKA_MESSAGE))).get();
        } catch (InterruptedException|ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 并发测试异步发送消息
     */
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void sendAsyncMessage(){
        producer.send(new ProducerRecord<Integer, String>(KafkaConfiguration.TOPIC, null, new String(KAFKA_MESSAGE)), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            }
        });
    }

    /**
     * 并发测试异步发送消息
     */
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void sendAsyncMessageWithResult(){
        try {
            Object o = producer.send(new ProducerRecord<Integer, String>(KafkaConfiguration.TOPIC, null, new String(KAFKA_MESSAGE)), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                }
            }).get();
        } catch (InterruptedException|ExecutionException e) {
            e.printStackTrace();
        }
    }
}
