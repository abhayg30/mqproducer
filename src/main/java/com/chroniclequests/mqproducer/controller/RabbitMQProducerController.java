package com.chroniclequests.mqproducer.controller;

import com.chroniclequests.mqproducer.dto.RabbitMQDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/live-location", produces = MediaType.APPLICATION_JSON_VALUE)
public class RabbitMQProducerController {

    private Logger logger = LoggerFactory.getLogger(RabbitMQProducerController.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducerController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/coordinates")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> generateLiveLocationCoordinate(@RequestParam(defaultValue = "") String sessionId,
                                                                 @RequestParam(defaultValue = "0.0") double lat,
                                                                 @RequestParam(defaultValue = "0.0") double lon,
                                                                 @RequestParam(defaultValue = "0.0") double radius){

        logger.info("Current coordinates with radius: " + "lat: " + lat + " lon: " + lon + " radius: " + radius);
        System.out.println("Code is here");
        String routingKey = "topic."+sessionId;
        RabbitMQDto rabbitMQDto = new RabbitMQDto(sessionId, lat, lon, radius);
        System.out.println(rabbitMQDto);
        rabbitTemplate.convertAndSend("topicExchange",routingKey, rabbitMQDto);

        return ResponseEntity.ok("Location sending for sessionId: "+sessionId);

    }

    //TODO: Handshake controller to dynamically create queues
    @PostMapping("handshake")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> handshakeForSessionCreation(@RequestParam String sessionId){
        System.out.println("handshake for session: "+sessionId+ " done");
        rabbitTemplate.convertAndSend("handshakeExchange", "handshake", sessionId);
        return ResponseEntity.ok("Session created successfully: "+sessionId);
    }
}
