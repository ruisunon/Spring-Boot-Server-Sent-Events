package com.example.sse.controller;

import com.example.sse.domain.Message;
import com.example.sse.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/sse")
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", allowCredentials = "true")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(path = "/health")
    public String healthCheck() {
        return "up";
    }

    @GetMapping("/user/messages")
    public Flux<ServerSentEvent<Message>> streamLastMessage(@RequestParam("name") String name) {
        return messageService.getNotificationEvent(name);
    }

}
