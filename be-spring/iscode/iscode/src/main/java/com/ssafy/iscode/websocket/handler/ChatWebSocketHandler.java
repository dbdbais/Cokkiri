package com.ssafy.iscode.websocket.handler;

import com.ssafy.iscode.message.service.MessageService;
import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriTemplate;

import java.util.*;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, Set<WebSocketSession>> chatRoomSessions = Collections.synchronizedMap(new HashMap<>());
    private final Map<WebSocketSession, String> userSessions = Collections.synchronizedMap(new HashMap<>());

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    // connected
    // room id, user name manage
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = getRoomId(session);
        String userName = getUserName(session);

        chatRoomSessions.computeIfAbsent(roomId, k -> Collections.synchronizedSet(new HashSet<>())).add(session);
        userSessions.put(session, userName);
    }

    // send message to all user in room
    // save message in db
    // separator = |!|
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = getRoomId(session);
        String userName = userSessions.get(session);
        String content = userName + "|!|" +
                message.getPayload();

        Set<WebSocketSession> sessions = chatRoomSessions.get(roomId);
        if (sessions != null) {
            messageService.saveMessage(Long.parseLong(roomId), userName, message.getPayload());

            for (WebSocketSession webSocketSession : sessions) {
                webSocketSession.sendMessage(new TextMessage(content));
            }
        }
    }

    // disconnected
    // room id, user name manage
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = getRoomId(session);
        userSessions.remove(session);

        Set<WebSocketSession> sessions = chatRoomSessions.get(roomId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                chatRoomSessions.remove(roomId);
            }
        }
    }

    private String getRoomId(WebSocketSession session) {
        UriTemplate template = new UriTemplate("/socket/chat/{roomId}/{userName}");
        Map<String, String> variables = template.match(session.getUri().getPath());
        return variables.get("roomId");
    }


    private String getUserName(WebSocketSession session) {
        UriTemplate template = new UriTemplate("/socket/chat/{roomId}/{userName}");
        Map<String, String> variables = template.match(session.getUri().getPath());
        Optional<User> user = userRepository.findByName(variables.get("userName"));

        if (user.isPresent()) {
            return user.get().getNickname();
        }

        throw new RuntimeException("Unknown user");
    }
}
