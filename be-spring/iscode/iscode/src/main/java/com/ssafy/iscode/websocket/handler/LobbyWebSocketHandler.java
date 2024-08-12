package com.ssafy.iscode.websocket.handler;

import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriTemplate;

import java.util.*;

@Component
public class LobbyWebSocketHandler extends TextWebSocketHandler {
    private final Set<WebSocketSession> lobbySessions = Collections.synchronizedSet(new HashSet<>());
    private final Map<String, WebSocketSession> userSessions = Collections.synchronizedMap(new HashMap<>());

    @Autowired
    private UserRepository userRepository;

    // connected
    // user name manage
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userName = getUserName(session);

        lobbySessions.add(session);
        userSessions.put(userName, session);
    }

    // no send message -> not chatting
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    }

    // disconnected
    // user name manage
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userName = getUserName(session);
        userSessions.remove(userName);

        if (!lobbySessions.isEmpty()) {
            lobbySessions.remove(session);
        }
    }

    private String getUserName(WebSocketSession session) {
        UriTemplate template = new UriTemplate("/lobby/{userName}");
        Map<String, String> variables = template.match(session.getUri().getPath());
        Optional<User> user = userRepository.findByName(variables.get("userName"));

        if (user.isPresent()) {
            return user.get().getNickname();
        }

        throw new RuntimeException("Unknown user");
    }

    // send event
    // server -> client
    public void sendEvent(String userName, String event) throws Exception {
        WebSocketSession session = userSessions.get(userName);
        if (session != null) {
            session.sendMessage(new TextMessage(event));
        }
    }

    // notification
//    private void broadcast(String message) throws Exception {
//        if (!lobbySessions.isEmpty()) {
//            for (WebSocketSession webSocketSession : lobbySessions) {
//                webSocketSession.sendMessage(new TextMessage(message));
//            }
//        }
//    }
}
