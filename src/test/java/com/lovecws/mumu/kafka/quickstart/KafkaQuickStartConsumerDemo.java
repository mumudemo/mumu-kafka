package com.lovecws.mumu.kafka.quickstart;

import org.junit.Test;

public class KafkaQuickStartConsumerDemo {

    @Test
    public void receiveMessage(){
        KafkaQuickStartConsumer consumer=new KafkaQuickStartConsumer();
        consumer.start();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
