package com.example.sse.service;

import java.time.Duration;

import com.example.sse.domain.Message;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class MessageService {

    public Flux<ServerSentEvent<Message>> getNotificationEvent(String name) {
        if (name != null && !name.isBlank()) {
            return Flux.interval(Duration.ofSeconds(2))
                    .map(sequence -> ServerSentEvent.<Message>builder().id(String.valueOf(sequence))
                            .event("notification-message-event").data(randomMessage()).build());
        }

        return Flux.interval(Duration.ofSeconds(2)).map(sequence -> ServerSentEvent.<Message>builder()
                .id(String.valueOf(sequence)).event("notification-message-event").data(null).build());
    }

    private Message randomMessage() {
        var id = Math.round(Math.random() * 100);
        return new Message((int)id, "message");
    }

}
