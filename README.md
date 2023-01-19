## To start the containers:
**microservices-demo/docker-compose$** `docker-compose -f common.yml -f kafka_cluster.yml up`

## To stop the containers:
**microservices-demo/docker-compose$** `docker-compose -f common.yml -f kafka_cluster.yml up`

## To query kafka:
**microservices-demo/docker-compose$** `docker run -it --network=host confluentinc/cp-kafkacat kafkacat -L -b localhost:19092`

## To query schema registry:
**$** curl -X GET http://localhost:8081/subjects

