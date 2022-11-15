package com.kamilisler.springwithkafka.service;

import com.kamilisler.springwithkafka.model.MappedPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

    @Service
    public class ProducerService {

        @Autowired
        private KafkaTemplate<String, Object> kafkaTemplate;

        @Value("${spring.kafka.producer.topic}")
        private String topicName;

        public void sendMessage(Object thePackage) {
            kafkaTemplate.send(topicName, thePackage);
            // template batch yollama araştır.
        }

    }

