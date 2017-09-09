# mumu-kafka kafka消息中间件
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/mumudemo/mumu-zbus/blob/master/LICENSE) [![Maven Central](https://img.shields.io/maven-central/v/com.weibo/motan.svg?label=Maven%20Central)](https://github.com/mumudemo/mumu-kafka) 
[![Build Status](https://travis-ci.org/mumudemo/mumu-kafka.svg?branch=master)](https://travis-ci.org/mumudemo/mumu-kafka)
[![codecov](https://codecov.io/gh/mumudemo/mumu-kafka/branch/master/graph/badge.svg)](https://codecov.io/gh/mumudemo/mumu-kafka)
[![OpenTracing-1.0 Badge](https://img.shields.io/badge/OpenTracing--1.0-enabled-blue.svg)](http://opentracing.io)

***Kafka™ is used for building real-time data pipelines and streaming apps. It is horizontally scalable, fault-tolerant, wicked fast, and runs in production in thousands of companies.***
![官网图片](http://kafka.apache.org/0110/images/kafka-apis.png)
*图片来自kafka官网*

## kafka简介
Apache Kafka是分布式发布-订阅消息系统。它最初由LinkedIn公司开发，之后成为Apache项目的一部分。Kafka是一种快速、可扩展的、设计内在就是分布式的，分区的和可复制的提交日志服务。

Apache Kafka与传统消息系统相比，有以下不同：
-   它被设计为一个分布式系统，易于向外扩展；
-   它同时为发布和订阅提供高吞吐量；
-   它支持多订阅者，当失败时能自动平衡消费者；
-   它将消息持久化到磁盘，因此可用于批量消费，例如ETL，以及实时应用程序。

## kafka构造
**主题和日志**  
主题是消息记录的集合，每一个kafka主题都可以拥有一个或者多个消息订阅者，当发送者发送消息之后，订阅该主题的消费者会获取到消息。  
![主题分区](http://kafka.apache.org/0110/images/log_anatomy.png)  
*图片来自kafka官网*

**生产者**  
Producers publish data to the topics of their choice. The producer is responsible for choosing which record to assign to which partition within the topic. This can be done in a round-robin fashion simply to balance load or it can be done according to some semantic partition function (say based on some key in the record). More on the use of partitioning in a second!

**消费者**  
Consumers label themselves with a consumer group name, and each record published to a topic is delivered to one consumer instance within each subscribing consumer group. Consumer instances can be in separate processes or on separate machines.

If all the consumer instances have the same consumer group, then the records will effectively be load balanced over the consumer instances.

If all the consumer instances have different consumer groups, then each record will be broadcast to all the consumer processes.  
![](http://kafka.apache.org/0110/images/consumer-groups.png)  
*图片来自kafka官网*


## kafka集成
**项目部署**  
根据官网介绍[quickstart](http://kafka.apache.org/quickstart)，按照相应的步骤一步一步运行即可。

当我按照文档一步一步安装完毕，在linux系统（将zookeeper、kafka安装在centos7虚拟机上）中可以正常的发送消息和接受消息，但是当我用主机测试发送消息和接受消息的时候总是报连接超时，刚接触kafka的我不知所错，按照程序员的习惯首先检查下自己编写的代码，前前后后不知道查了多少遍之后，发现自己的代码没有问题，然后就开始百度，才发现这个问题是由于kafka绑定了hostname所致，将dvertised.host.name、host.name修改为自己的ip地址即可，然后修改kafka server.properties配置文件即可。
```
dvertised.host.name=192.168.0.22
advertised.port=9092
host.name=192.168.0.22
```

**发送消息**
```
    public void sendMessage(String message,int count) {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put("client.id", "test-proceducer-group");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer<>(props);

        try {
            for (int i = 0; i < count; i++) {
                Object o = producer.send(new ProducerRecord(KafkaConfiguration.TOPIC, null, message)).get();
                System.out.println("send message:" + o);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
```
**接受消息**
```
public class KafkaQuickStartConsumer extends ShutdownableThread {

    private KafkaConsumer consumer;

    public KafkaQuickStartConsumer(){
        super("KafkaQuickStartConsumer",false);
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfiguration.BOOTSTRAP_SERVERS_CONFIG);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaConfiguration.KEY_DESERIALIZER_CLASS_CONFIG);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaConfiguration.VALUE_DESERIALIZER_CLASS_CONFIG);

        consumer = new KafkaConsumer<>(props);
    }

    @Override
    public void doWork() {
        consumer.subscribe(Collections.singleton(KafkaConfiguration.TOPIC));
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        for (ConsumerRecord<Integer, String> record : records) {
            System.out.println("Received message: (" +record);
        }
    }
}
```

## 测试统计

## 相关阅读  
[Apache-kafka官网文档](http://kafka.apache.org/documentation)   
[kafka学习笔记：知识点整理](http://www.cnblogs.com/cyfonly/p/5954614.html)

## 联系方式
**以上观点纯属个人看法，如有不同，欢迎指正。  
email:<babymm@aliyun.com>  
github:[https://github.com/babymm](https://github.com/babymm)**