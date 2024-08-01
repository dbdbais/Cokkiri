package com.ssafy.iscode.websocket.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriTemplate;

import java.util.*;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, Set<WebSocketSession>> chatRoomSessions = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = getRoomId(session);
        chatRoomSessions.computeIfAbsent(roomId, k -> Collections.synchronizedSet(new HashSet<>())).add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = getRoomId(session);
        Set<WebSocketSession> sessions = chatRoomSessions.get(roomId);
        if (sessions != null) {
            for (WebSocketSession webSocketSession : sessions) {
                webSocketSession.sendMessage(new TextMessage(message.getPayload()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = getRoomId(session);
        Set<WebSocketSession> sessions = chatRoomSessions.get(roomId);
        if (sessions != null) {
            sessions.remove(session);
            if(sessions.isEmpty()) {
                chatRoomSessions.remove(roomId);
            }
        }
    }

    private String getRoomId(WebSocketSession session) {
        UriTemplate template = new UriTemplate("/chat/{roomId}");
        Map<String, String> variables = template.match(session.getUri().getPath());
        return variables.get("roomId");
    }
}
