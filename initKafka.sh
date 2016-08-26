#!/usr/bin/env bash

#TODO test for if mac
export EXTERNAL_IP=$(ifconfig en0 | awk '/inet / {print $2}')

docker-compose up -d

echo "Sleeping 10 seconds for Brokers to register with Zookeeper..."
sleep 10

docker exec -i -t springbootkafka_kafka_a_1 bin/bash -c '$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper zk:2181 --replication-factor 3 --partitions 100 --topic topic3-100'
