package net.dancier.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class KafkaProducerApplication {

	final static Logger LOG = LoggerFactory.getLogger(KafkaProducerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
		// tag::create[]
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.serializer", LongSerializer.class.getName());
		props.put("value.serializer", StringSerializer.class.getName());

		KafkaProducer<Long, String> producer = new KafkaProducer<>(props);
		// end::create[]

		try
		{
			for (long i = 0; i < 10000; i++)
			{
				long time = System.currentTimeMillis();
				String message = "Hallo Welt #" + i;

				ProducerRecord<Long, String> record = new ProducerRecord<>(
						"test",    // Topic
						message    // Value
				);

				producer.send(record, (metadata, e) ->
				{
					long now = System.currentTimeMillis();
					if (e == null)
					{
						// HANDLE SUCCESS
						LOG.info(
								"{}: sent #{} timestamp={} latency={}ms",
								now,
								record.key(),
								metadata.timestamp(),
								now - time
						);
					}
					else
					{
						// HANDLE ERROR
						LOG.error(
								"{}: ERROR #{} timestamp={} latency={}ms: {}",
								now,
								record.key(),
								metadata == null ? -1 : metadata.timestamp(),
								now - time,
								e.toString()
						);
					}
				});

				long now = System.currentTimeMillis();
				LOG.info(
						"{}: queued #{} latency={}ms",
						now,
						record.key(),
						now - time
				);
			}
		}
		finally
		{
			LOG.info("Closing producer...");
			producer.close();
			LOG.info("DONE!");
		}
	}
}
