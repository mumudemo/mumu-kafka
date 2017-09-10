package com.lovecws.mumu.kafka.quickstart;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class KafkaQuickStartConsumerTest {

    @Test
    public void receiveMessage(){
        KafkaQuickStartConsumer consumer=new KafkaQuickStartConsumer();
        consumer.start();
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
