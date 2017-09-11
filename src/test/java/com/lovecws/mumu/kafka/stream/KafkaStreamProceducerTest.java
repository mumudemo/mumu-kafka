package com.lovecws.mumu.kafka.stream;

import org.junit.Test;

public class KafkaStreamProceducerTest {

    @Test
    public void sendMessage(){
        KafkaStreamProceducer proceducer=new KafkaStreamProceducer();
        proceducer.sendMessage("我爱你，我的宝宝小慕慕",1);
    }
}
