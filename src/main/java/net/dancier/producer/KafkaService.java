package net.dancier.producer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final ProducerFactory producerFactory;

    public void sendMessage(String topic, String message) {
        Producer producer = producerFactory.createProducer();
        ProducerRecord<Long, String> record = new ProducerRecord<>(
                "huhu",    // Topic
                message    // Value
        );
        producer.send(record);
    }
}
