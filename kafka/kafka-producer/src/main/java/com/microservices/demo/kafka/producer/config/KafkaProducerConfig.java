package com.microservices.demo.kafka.producer.config;

import java.io.Serializable;

@Configuration
public class KafkaProducerConfig<K extends Serializable, V extends SpecificRecordBase {
    private final kafkaConfigData kafkaConfigData;
    private final KafkaProducerConfigData KafkaProducerConfigData;


    
}