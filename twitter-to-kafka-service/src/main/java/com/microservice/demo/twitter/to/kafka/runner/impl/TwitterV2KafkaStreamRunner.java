package com.microservice.demo.twitter.to.kafka.runner.impl;

import com.microservice.demo.twitter.to.kafka.config.TwitterToKafkaConfigData;
import com.microservice.demo.twitter.to.kafka.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
//@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-v2-tweets", havingValue = "true", matchIfMissing = true)
@ConditionalOnExpression("${twitter-to-kafka-service.enable-v2-tweets} && not ${twitter-to-kafka-service.enable-mock-tweets}")
public class TwitterV2KafkaStreamRunner implements StreamRunner {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterV2KafkaStreamRunner.class);

    private final TwitterToKafkaConfigData twitterToKafkaConfigData;
    private final TwitterV2StreamHelper twitterV2StreamHelper;

    public TwitterV2KafkaStreamRunner(TwitterToKafkaConfigData twitterToKafkaConfigData, TwitterV2StreamHelper twitterV2StreamHelper) {
        this.twitterToKafkaConfigData = twitterToKafkaConfigData;
        this.twitterV2StreamHelper = twitterV2StreamHelper;
    }

    @Override
    public void start() {
        String bearerToken = twitterToKafkaConfigData.getTwitterV2BearerToken();
        if(null != bearerToken ){
            try {
                twitterV2StreamHelper.setupRules(bearerToken, getRules());
                twitterV2StreamHelper.connectStream(bearerToken);
            } catch (IOException | URISyntaxException e){
                String error = "Error streaming tweets!";
                LOG.error(error, e);
                throw new RuntimeException(error, e);

            }
        } else {
            String error = "There was a problem getting your bearer token. Pls make sure that you set the TWITTER_BEARER_TOKEN environment variable";
            LOG.error(error);
            throw new RuntimeException(error);
        }
    }

    private Map<String, String> getRules(){
        List<String> keywords = twitterToKafkaConfigData.getTwitterKeywords();
        Map<String, String> rules = new HashMap<>();
        for(String keyword: keywords){
            rules.put(keyword, "keyword: " + keyword);
        }
        LOG.info("created filter for twitter stream for keywords: {}", keywords);
        return rules;
    }
}
