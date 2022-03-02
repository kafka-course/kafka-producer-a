# First naive approach on sending messages to Kafka

## What this example project shows

* set up a two broker kafka cluster with docker-compose
* send messages, ignoring any response from the Producer-API

## Let it run

### Setup Infrastructure
``
docker-compose up -d
``
### Run the example programm
``
./mvnw spring-boot:run
``
### Check that message has been send:

[Web UI (http://localhost:9000)](http://localhost:9000)

### Stop the infrastructure

``
docker-compose down
``