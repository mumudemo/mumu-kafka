package com.lovecws.mumu.kafka;

public class KafkaConfiguration {

    public static String BOOTSTRAP_SERVERS_CONFIG=null;

    public static final String KEY_DESERIALIZER_CLASS_CONFIG="org.apache.kafka.common.serialization.IntegerDeserializer";
    public static final String VALUE_DESERIALIZER_CLASS_CONFIG="org.apache.kafka.common.serialization.StringDeserializer";

    public static final String TOPIC="babymm";

    static {
        //从环境变量中 获取
        String KAFKASERVICES = System.getenv("KAFKASERVICES");
        if(KAFKASERVICES!=null){
            BOOTSTRAP_SERVERS_CONFIG=KAFKASERVICES;
        }else{
            BOOTSTRAP_SERVERS_CONFIG="192.168.11.25:9092";
        }
    }
}
