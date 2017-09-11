package com.lovecws.mumu.kafka.stream;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class KafkaStreamWordCountTest {

    @Test
    public void wordCount(){
        KafkaStreamWordCount stream=new KafkaStreamWordCount();
        stream.wordCount();
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void wordCountProcessor(){
        KafkaStreamWordCount stream=new KafkaStreamWordCount();
        stream.wordCountProcessor();
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
