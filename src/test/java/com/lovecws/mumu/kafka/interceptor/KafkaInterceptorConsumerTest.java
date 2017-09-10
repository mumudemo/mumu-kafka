package com.lovecws.mumu.kafka.interceptor;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class KafkaInterceptorConsumerTest {

    @Test
    public void receiveInteceptorMessage(){
        KafkaInterceptorConsumer consumer=new KafkaInterceptorConsumer();
        consumer.start();
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
