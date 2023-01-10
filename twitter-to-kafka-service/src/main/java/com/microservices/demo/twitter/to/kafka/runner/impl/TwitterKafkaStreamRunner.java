package com.microservices.demo.twitter.to.kafka.runner.impl;

import com.microservices.demo.twitter.to.kafka.listener.TwitterKafkaStatusListener;
import com.microservices.demo.twitter.to.kafka.runner.StreamRunner;
import com.microservices.demo.config.TwitterToKafkaConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;
import java.util.Arrays;

@Component
@ConditionalOnExpression("not ${twitter-to-kafka-service.enable-v2-tweets} && not ${twitter-to-kafka-service.enable-mock-tweets}")
//@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-v2-tweets", havingValue = "false")
public class TwitterKafkaStreamRunner implements StreamRunner {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStreamRunner.class);
    private final TwitterToKafkaConfigData twitterToKafkaConfigData;
    private final TwitterKafkaStatusListener twitterKafkaStatusListener;
    private TwitterStream twitterStream;

    public TwitterKafkaStreamRunner(TwitterToKafkaConfigData twitterToKafkaConfigData, TwitterKafkaStatusListener twitterKafkaStatusListener) {
        this.twitterToKafkaConfigData = twitterToKafkaConfigData;
        this.twitterKafkaStatusListener = twitterKafkaStatusListener;
    }

    @Override
    public void start() throws TwitterException {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterKafkaStatusListener);
        addFilter();
    }

    @PreDestroy
    public void shutdown() {
        if (twitterStream != null) {
            LOG.info("Closing twitter stream");
            twitterStream.shutdown();

        }
    }

    private void addFilter() {
        String[] keywords = twitterToKafkaConfigData.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        LOG.info("Started filtering twitter stream for keywords {}", Arrays.toString(keywords));
    }
}
