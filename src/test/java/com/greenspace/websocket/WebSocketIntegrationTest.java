package com.greenspace.websocket;

import com.greenspace.dto.request.ChatMessageRequest;
import com.greenspace.dto.response.ChatMessageResponse;
import com.greenspace.security.JwtTokenProvider;
import lombok.NonNull;
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

    private WebSocketStompClient stompClient;

    @BeforeEach
    void setup() {
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    void shouldReceiveMessageAfterSending() throws ExecutionException, InterruptedException, TimeoutException {
        // 1. Simuler un token JWT pour l'utilisateur de test (id=1, ex: le destinataire)
        // Note: Assurez-vous d'avoir un utilisateur de test avec email='test@test.com' dans votre BDD H2 pour le test
        String token = jwtTokenProvider.generateToken(
                new UsernamePasswordAuthenticationToken("test@test.com", "password", Collections.emptyList())
        );

        String url = "ws://localhost:" + port + "/ws";

        StompHeaders headers = new StompHeaders();
        headers.add("Authorization", "Bearer " + token);

        // 2. Connexion du client WebSocket
        StompSession session = stompClient.connectAsync(url, new WebSocketHttpHeaders(), headers, new StompSessionHandlerAdapter() {}).get(5, TimeUnit.SECONDS);

        // 3. CompletableFuture pour capturer le message reçu asynchrone
        CompletableFuture<ChatMessageResponse> resultKeeper = new CompletableFuture<>();

        // 4. Souscription à la file privée
        session.subscribe("/user/queue/messages", new StompFrameHandler() {
            @Override
            public Type getPayloadType(@NonNull StompHeaders headers) {
                return ChatMessageResponse.class;
            }

            @Override
            public void handleFrame(@NonNull StompHeaders headers, Object payload) {
                resultKeeper.complete((ChatMessageResponse) payload);
            }
        });

        // 5. Envoi du message
        ChatMessageRequest request = ChatMessageRequest.builder()
                .recipientId(1L) // S'envoie à lui-même pour simplifier le test
                .content("Hello WebSocket Test")
                .build();

        session.send("/app/chat.send", request);

        // 6. Vérification du résultat reçu
        ChatMessageResponse receivedMessage = resultKeeper.get(5, TimeUnit.SECONDS);
        assertNotNull(receivedMessage);
        assertEquals("Hello WebSocket Test", receivedMessage.getContent());
    }
}