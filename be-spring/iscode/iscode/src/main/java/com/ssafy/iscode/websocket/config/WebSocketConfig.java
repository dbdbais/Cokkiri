package com.ssafy.iscode.websocket.config;

import com.ssafy.iscode.websocket.handler.ChatWebSocketHandler;
import com.ssafy.iscode.websocket.handler.LobbyWebSocketHandler;
import com.ssafy.iscode.websocket.handler.RoomWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // message socket
        registry.addHandler(ChatWebSocketHandler(), "/chat/{roomId}/{userName}")
                .setAllowedOrigins("*");

        // lobby socket
        registry.addHandler(LobbyWebSocketHandler(), "/lobby")
                .setAllowedOrigins("*");

        // room socket
        registry.addHandler(RoomWebSocketHandler(), "/room/{roomId}/{userName}")
                .setAllowedOrigins("*");
    }

    // to resolve a bug
    @Bean
    public ChatWebSocketHandler ChatWebSocketHandler() {
        return new ChatWebSocketHandler();
    }

    @Bean
    public LobbyWebSocketHandler LobbyWebSocketHandler() {
        return new LobbyWebSocketHandler();
    }

    @Bean
    public RoomWebSocketHandler RoomWebSocketHandler() {
        return new RoomWebSocketHandler();
    }
}
