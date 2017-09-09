package com.lovecws.mumu.kafka.quickstart;

import org.junit.Test;

public class KafkaQuickStartProceducerDemo {

    @Test
    public void sendMessage(){
        KafkaQuickStartProceducer proceducer=new KafkaQuickStartProceducer();
        proceducer.sendMessage("lovecws",1000);
    }

    @Test
    public void sendAsyncMessage(){
        KafkaQuickStartProceducer proceducer=new KafkaQuickStartProceducer();
        proceducer.sendAsyncMessage("lovecws",1000);
    }
}
