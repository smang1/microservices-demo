package com.microservices.demo.twitter.to.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = "com.microservice.demo")
public class TwitterToKafkaServiceApplicationTests {
    @Test
    public void contextLoad() {

    }
}