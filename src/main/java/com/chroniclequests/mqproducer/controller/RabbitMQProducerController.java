package com.chroniclequests.mqproducer.controller;

import com.chroniclequests.mqproducer.dto.RabbitMQDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/live-location", produces = MediaType.APPLICATION_JSON_VALUE)
public class RabbitMQProducerController {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducerController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/coordinates")
    public ResponseEntity<String> generateLiveLocationCoordinate(@RequestParam String sessionId,
                                                                 @RequestParam double lat,
                                                                 @RequestParam double lon){
        System.out.println("Code is here");
        String routingKey = "topic."+sessionId;
        RabbitMQDto rabbitMQDto = new RabbitMQDto(sessionId, lat, lon);
        rabbitTemplate.convertAndSend("topicExchange",routingKey, rabbitMQDto);

        return ResponseEntity.ok("Location sending for sessionId: "+sessionId);

    }

    //TODO: Handshake controller to dynamically create queues
    @PostMapping("handshake")
    public ResponseEntity<String> handshakeForSessionCreation(@RequestParam String sessionId){
        System.out.println("handshake for session: "+sessionId+ " done");
        rabbitTemplate.convertAndSend("handshakeExchange", "handshake", sessionId);
        return ResponseEntity.ok("Session created successfully: "+sessionId);
    }
}
