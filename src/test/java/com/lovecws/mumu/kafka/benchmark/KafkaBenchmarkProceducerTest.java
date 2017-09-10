package com.lovecws.mumu.kafka.benchmark;

import org.junit.Ignore;
import org.junit.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.reflect.Method;

public class KafkaBenchmarkProceducerTest {

    @Test
    @Ignore
    public void sendMessage(){
        Options optional=new OptionsBuilder()
                .forks(1)
                .measurementIterations(10)
                .warmupIterations(10)
                .threads(1)
                .include(KafkaBenchmarkProceducer.class.getSimpleName()+".sendMessage$")
                .build();
        try {
            Runner runner = new Runner(optional);
            runner.run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void sendMessageWithResult(){
        Options optional=new OptionsBuilder()
                .forks(1)
                .measurementIterations(10)
                .warmupIterations(10)
                .threads(1)
                .include(KafkaBenchmarkProceducer.class.getSimpleName()+".sendMessageWithResult$")
                .build();
        try {
            Runner runner = new Runner(optional);
            runner.run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void sendAsyncMessage(){
        try {
            Method method = KafkaBenchmarkProceducer.class.getMethod("sendAsyncMessage", null);
            Options optional=new OptionsBuilder()
                    .forks(1)
                    .measurementIterations(10)
                    .warmupIterations(10)
                    .threads(1)
                    .include(method.getName()+"$")
                    .build();
            Runner runner = new Runner(optional);
            runner.run();
        } catch (RunnerException|NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void sendAsyncMessageWithResult(){
        KafkaBenchmarkProceducer proceducer=new KafkaBenchmarkProceducer();
        try {
            Method method = KafkaBenchmarkProceducer.class.getMethod("sendAsyncMessageWithResult", null);
            Options optional=new OptionsBuilder()
                    .forks(1)
                    .measurementIterations(10)
                    .warmupIterations(10)
                    .threads(1)
                    .include(method.getName()+"$")
                    .build();
            Runner runner = new Runner(optional);
            runner.run();
        } catch (RunnerException|NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
