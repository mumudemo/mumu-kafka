package com.lovecws.mumu.kafka.stream;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class KafkaStreamConsumerTest {

    @Test
    public void receiveMessage(){
        KafkaStreamConsumer consumer=new KafkaStreamConsumer();
        consumer.start();
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
