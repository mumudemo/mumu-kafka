package com.lovecws.mumu.kafka.transaction;

import org.junit.Test;

public class kafkaTransactionProceducerTest {

    @Test
    public void sendTransactionMessage(){
        kafkaTransactionProceducer proceducer = new kafkaTransactionProceducer();
        proceducer.sendTransactionMessage("事务消息测试",3);
    }
}
