package com.lovecws.mumu.kafka.stream;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Locale;

public class KafkaStreamWordCountProcessorSupplier implements ProcessorSupplier<String, String> {
    public KafkaStreamWordCountProcessorSupplier() {
    }

    @Override
    public Processor<String, String> get() {
        return new KafkaStreamWordCountProcessor();
    }

    public static class KafkaStreamWordCountProcessor implements Processor<String, String> {
        private ProcessorContext context;
        private KeyValueStore<String, Integer> kvStore;

        public void init(ProcessorContext context) {
            this.context = context;
            this.context.schedule(1000L);
            this.kvStore = (KeyValueStore)context.getStateStore("Counts");
        }

        public void process(String dummy, String line) {
            String[] words = line.toLowerCase(Locale.getDefault()).split(" ");
            String[] arr$ = words;
            int len$ = words.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String word = arr$[i$];
                Integer oldValue = (Integer)this.kvStore.get(word);
                if (oldValue == null) {
                    this.kvStore.put(word, Integer.valueOf(1));
                } else {
                    this.kvStore.put(word, oldValue.intValue() + 1);
                }
            }

            this.context.commit();
        }

        public void punctuate(long timestamp) {
            KeyValueIterator<String, Integer> iter = this.kvStore.all();
            Throwable var4 = null;

            try {
                System.out.println("----------- " + timestamp + " ----------- ");

                while(iter.hasNext()) {
                    KeyValue<String, Integer> entry = (KeyValue)iter.next();
                    System.out.println("[" + (String)entry.key + ", " + entry.value + "]");
                    this.context.forward(entry.key, ((Integer)entry.value).toString());
                }
            } catch (Throwable var13) {
                var4 = var13;
                throw var13;
            } finally {
                if (iter != null) {
                    if (var4 != null) {
                        try {
                            iter.close();
                        } catch (Throwable var12) {
                            var4.addSuppressed(var12);
                        }
                    } else {
                        iter.close();
                    }
                }

            }

        }

        public void close() {
        }
    }
}