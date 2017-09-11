package com.lovecws.mumu.kafka.stream;

import com.lovecws.mumu.kafka.KafkaConfiguration;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.apache.kafka.streams.state.Stores;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

public class KafkaStreamWordCount {

    /**
     * 计算字符数量
     */
    public void wordCount(){
        Properties props = new Properties();
        props.put("application.id", "KafkaStreamWordCount");
        props.put("bootstrap.servers", KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put("cache.max.bytes.buffering", Integer.valueOf(0));
        props.put("default.key.serde", Serdes.String().getClass().getName());
        props.put("default.value.serde", Serdes.String().getClass().getName());
        props.put("auto.offset.reset", "earliest");

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> source = builder.stream(new String[]{"streams-file-input"});
        KTable<String, Long> counts = source.flatMapValues(new ValueMapper<String, Iterable<String>>() {
            public Iterable<String> apply(String value) {
                return Arrays.asList(value.toLowerCase(Locale.getDefault()).split(" "));
            }
        }).map(new KeyValueMapper<String, String, KeyValue<String, String>>() {
            public KeyValue<String, String> apply(String key, String value) {
                return new KeyValue(value, value);
            }
        }).groupByKey().count("Counts");
        counts.to(Serdes.String(), Serdes.Long(), "streams-wordcount-output");
        KafkaStreams streams = new KafkaStreams(builder, props);
        streams.start();
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        streams.close();
    }

    /**
     * 计算字符数量
     */
    public void wordCountProcessor(){
        Properties props = new Properties();
        props.put("application.id", "KafkaStreamWordCountProcessor");
        props.put("bootstrap.servers", KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put("cache.max.bytes.buffering", Integer.valueOf(0));
        props.put("default.key.serde", Serdes.String().getClass().getName());
        props.put("default.value.serde", Serdes.String().getClass().getName());
        props.put("auto.offset.reset", "earliest");

        TopologyBuilder builder = new TopologyBuilder();
        builder.addSource("Source", new String[]{"streams-file-input"});
        builder.addProcessor("Process", new KafkaStreamWordCountProcessorSupplier(), new String[]{"Source"});
        builder.addStateStore(Stores.create("Counts").withStringKeys().withIntegerValues().inMemory().build(), new String[]{"Process"});
        builder.addSink("Sink", "streams-wordcount-processor-output", new String[]{"Process"});

        KafkaStreams streams = new KafkaStreams(builder, props);
        streams.start();
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        streams.close();
    }
}
