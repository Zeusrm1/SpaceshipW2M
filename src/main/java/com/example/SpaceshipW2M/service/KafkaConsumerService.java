package com.example.SpaceshipW2M.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "spaceship_topic", groupId = "group_id")
    public void consume(Object message) {
        System.out.println("Consumed message: " + message);
    }
}