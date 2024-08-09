package com.ssafy.iscode.websocket.config;

import com.ssafy.iscode.websocket.handler.ChatWebSocketHandler;
import com.ssafy.iscode.websocket.handler.GameWebSocketHandler;
import com.ssafy.iscode.websocket.handler.LobbyWebSocketHandler;
import com.ssafy.iscode.websocket.handler.RoomWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private LobbyWebSocketHandler lobbyWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // message socket
        registry.addHandler(ChatWebSocketHandler(), "/socket/chat/{roomId}/{userName}")
                .setAllowedOrigins("*");

        // lobby socket
        registry.addHandler(lobbyWebSocketHandler, "/socket/lobby/{userName}")
                .setAllowedOrigins("*");

        // room socket
        registry.addHandler(RoomWebSocketHandler(), "/socket/room/{roomId}/{userName}")
                .setAllowedOrigins("*");

        // room socket
        registry.addHandler(GameWebSocketHandler(), "/socket/game/{roomId}/{userName}")
                .setAllowedOrigins("*");
    }

    // to resolve a bug
    @Bean
    public ChatWebSocketHandler ChatWebSocketHandler() {
        return new ChatWebSocketHandler();
    }

    @Bean
    public RoomWebSocketHandler RoomWebSocketHandler() {
        return new RoomWebSocketHandler();
    }

    @Bean
    public GameWebSocketHandler GameWebSocketHandler() {
        return new GameWebSocketHandler();
    }
}
