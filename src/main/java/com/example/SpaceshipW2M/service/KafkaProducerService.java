package com.example.SpaceshipW2M.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Value("${kafka.producer.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Object message) {
        kafkaTemplate.send(topic, message);
    }
}