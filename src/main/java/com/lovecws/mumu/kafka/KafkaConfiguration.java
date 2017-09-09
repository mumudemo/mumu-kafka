package com.lovecws.mumu.kafka;

public class KafkaConfiguration {

    public static final String BOOTSTRAP_SERVERS_CONFIG="192.168.0.22:9092";

    public static final String KEY_DESERIALIZER_CLASS_CONFIG="org.apache.kafka.common.serialization.IntegerDeserializer";
    public static final String VALUE_DESERIALIZER_CLASS_CONFIG="org.apache.kafka.common.serialization.StringDeserializer";

    public static final String TOPIC="babymm";
}
