package com.example.SpaceshipW2M.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpaceshipW2M.service.KafkaProducerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Kafka API", description = "API for sending messages to Kafka")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/sendMessage")
    @Operation(summary = "Send a message to Kafka", description = "Sends a message to the configured Kafka topic")
    public void sendMessage(@RequestBody Object message) {
        kafkaProducerService.sendMessage(message);
    }
}