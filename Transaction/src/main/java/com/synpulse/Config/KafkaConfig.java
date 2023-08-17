package com.synpulse.Config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

@Configuration
public class KafkaConfig {
    //public static String bootstrapServers = "host.docker.internal:9092";
    public static String bootstrapServers = "localhost:9092";

    @Bean
    public KafkaProducer<String, String> kafkaProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "5");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        return kafkaProducer;
    }


    @Bean
    @Primary
    public KafkaConsumer<String, String> kafkaConsumer() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        return kafkaConsumer;
    }

    @Bean(name = "KafkaConsumer2")
    public KafkaConsumer<String, String> kafkaConsumer2() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 101);
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        return kafkaConsumer;
    }

    @Bean(name = "KafkaConsumer3")
    public KafkaConsumer<String, String> kafkaConsumer3() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        return kafkaConsumer;
    }

}
