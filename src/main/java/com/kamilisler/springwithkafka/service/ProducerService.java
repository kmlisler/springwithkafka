package com.kamilisler.springwithkafka.service;

import com.kamilisler.springwithkafka.model.MappedPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String topicName;

    public void sendMessage(MappedPackage thePackage) {
        kafkaTemplate.send(topicName, thePackage);
    }

    public void sendMessageForBootstrap(List<MappedPackage> thePackages) {

        thePackages.forEach(this::sendMessage);

    }

}

