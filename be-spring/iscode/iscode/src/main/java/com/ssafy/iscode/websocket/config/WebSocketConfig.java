package com.ssafy.iscode.websocket.config;

import com.ssafy.iscode.websocket.handler.ChatWebSocketHandler;
import com.ssafy.iscode.websocket.handler.LobbyWebSocketHandler;
import com.ssafy.iscode.websocket.handler.RoomWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(), "/chat/{roomId}").setAllowedOrigins("*");
        registry.addHandler(new LobbyWebSocketHandler(), "/lobby").setAllowedOrigins("*");
        registry.addHandler(new RoomWebSocketHandler(), "/room/{roomId}").setAllowedOrigins("*");
    }
}
