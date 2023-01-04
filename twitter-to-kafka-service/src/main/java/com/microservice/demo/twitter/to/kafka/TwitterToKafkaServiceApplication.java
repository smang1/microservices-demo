package com.microservice.demo.twitter.to.kafka;

import com.microservice.demo.twitter.to.kafka.config.TwitterToKafkaConfigData;
import com.microservice.demo.twitter.to.kafka.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);
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
