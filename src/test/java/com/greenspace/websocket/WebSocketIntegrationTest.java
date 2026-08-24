package com.greenspace.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.greenspace.dto.request.ChatMessageRequest;
import com.greenspace.dto.response.ChatMessageResponse;
import com.greenspace.entity.User;
import com.greenspace.enums.Role;
import com.greenspace.repository.UserRepository;
import com.greenspace.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSocketIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    private WebSocketStompClient stompClient;

    @BeforeEach
    void setup() {
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        stompClient.setMessageConverter(converter);

        if (!userRepository.existsByEmail("test@test.com")) {
            User testUser = User.builder()
                    .email("test@test.com")
                    .password("password")
                    .firstName("Test")
                    .lastName("User")
                    .role(Role.GARDENER)
                    .isBlocked(false)
                    .build();
            userRepository.save(testUser);
        }
    }

    @Test
    void shouldReceiveMessageAfterSending() throws ExecutionException, InterruptedException, TimeoutException {
        String token = jwtTokenProvider.generateToken(
                new UsernamePasswordAuthenticationToken("test@test.com", "password", Collections.emptyList())
        );

        String url = "ws://localhost:" + port + "/ws/websocket";

        StompHeaders headers = new StompHeaders();
        headers.add("Authorization", "Bearer " + token);

        StompSession session = stompClient.connectAsync(url, new WebSocketHttpHeaders(), headers, new StompSessionHandlerAdapter() {}).get(5, TimeUnit.SECONDS);

        CompletableFuture<ChatMessageResponse> resultKeeper = new CompletableFuture<>();

        session.subscribe("/user/queue/messages", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessageResponse.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                resultKeeper.complete((ChatMessageResponse) payload);
            }
        });

        User testUser = userRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long actualUserId = testUser.getId();

        ChatMessageRequest request = ChatMessageRequest.builder()
                .recipientId(actualUserId)
                .content("Hello WebSocket Test")
                .build();

        Thread.sleep(500);

        session.send("/app/chat.send", request);

        ChatMessageResponse receivedMessage = resultKeeper.get(5, TimeUnit.SECONDS);
        assertNotNull(receivedMessage);
        assertEquals("Hello WebSocket Test", receivedMessage.getContent());
    }
}