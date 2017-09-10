package com.lovecws.mumu.kafka.transaction;

import com.lovecws.mumu.kafka.KafkaConfiguration;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 事物消息发送
 * 必须将事务消息发送到topic的同个分区中
 * kafka保证在同一个分区的发送顺序  而且事务消息需要顺序消息
 */
public class kafkaTransactionProceducer {

    /**
     * 发送事务消息
     * @param message 消息
     * @param transactionCount 事务消息的数量
     */
    public void sendTransactionMessage(String message,int transactionCount) {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put("client.id", "kafkaTransactionProceducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("enable.idempotence", true);
        props.put("transactional.id", "babymm123456");
        props.put("transaction.timeout.ms", 2000);
        props.put("retry.backoff.ms", 2000);
        KafkaProducer<Integer,String> producer = new KafkaProducer<Integer,String>(props);

        //初始化事务
        producer.initTransactions();

        //开启事务
        producer.beginTransaction();
        try {
            for (int i = 0; i < transactionCount; i++) {
                //kafka保证相同的partition 和key
                Object o = producer.send(new ProducerRecord<Integer, String>(KafkaConfiguration.TOPIC, 0,123, message)).get();
                System.out.println("send message:" + o);
            }
        } catch (InterruptedException|ExecutionException e) {
            e.printStackTrace();
            //消息发送异常 终端事务
            producer.abortTransaction();
        }
        //所有的消息都发送成功之后 才提交事务
        producer.commitTransaction();
    }

}
