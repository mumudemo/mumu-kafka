package com.lovecws.mumu.kafka.cluster;

import org.junit.Test;

public class KafkaClusterProceducerTest {

    @Test
    public void sendClusterMessage(){
        KafkaClusterProceducer proceducer=new KafkaClusterProceducer();
        proceducer.sendClusterMessage();
    }
}
