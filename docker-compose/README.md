
**microservices-demo/docker-compose$** `docker-compose -f common.yml -f kafka_cluster.yml up`
**microservices-demo/docker-compose$** `docker run -it --network=host confluentinc/cp-kafkacat kafkacat -L -b localhost:19092`
