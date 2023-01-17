package com.microservices.demo.twitter.to.kafka;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.microservices.demo.config.TwitterToKafkaConfigData;
import com.microservices.demo.twitter.to.kafka.runner.StreamRunner;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.demo")
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);
    private final TwitterToKafkaConfigData twitterToKafkaConfigData;
    private final StreamRunner streamRunner;

    public TwitterToKafkaServiceApplication(TwitterToKafkaConfigData configData, StreamRunner streamRunner) {
        this.twitterToKafkaConfigData = configData;
        this.streamRunner = streamRunner;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("App starts");
        LOG.info(Arrays.toString(twitterToKafkaConfigData.getTwitterKeywords().toArray()));
        LOG.info(twitterToKafkaConfigData.getWelcomeMessage());
        streamRunner.start();
    }
}
