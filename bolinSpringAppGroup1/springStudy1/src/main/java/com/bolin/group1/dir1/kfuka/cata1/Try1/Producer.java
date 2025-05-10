package com.bolin.group1.dir1.kfuka.cata1.Try1;



import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;



public class Producer {

    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    public static void main(String[] args){

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {

            producer.send(new ProducerRecord<>("quickstart-events", "some-message5"), (metadata, exception) -> {
                if (exception != null) {
                    log.error("Trouble producing", exception);
                } else {
                    log.info("Produced record ({}) at offset {} to topic {}",
                            "message", metadata.offset(), metadata.topic());


                }
            });
        }
    }
}
