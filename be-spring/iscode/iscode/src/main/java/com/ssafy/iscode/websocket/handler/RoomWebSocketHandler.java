package com.ssafy.iscode.websocket.handler;

import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.waitingroom.model.dao.StudyRepository;
import com.ssafy.iscode.waitingroom.model.dto.StudyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriTemplate;

import java.util.*;

public class RoomWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, Set<WebSocketSession>> roomSessions = Collections.synchronizedMap(new HashMap<>());
    private final Map<WebSocketSession, String> userSessions = Collections.synchronizedMap(new HashMap<>());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    // connected
    // room id, user name manage
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = getRoomId(session);
        String userName = getUserName(session);

        roomSessions.computeIfAbsent(roomId, k -> Collections.synchronizedSet(new HashSet<>())).add(session);
        userSessions.put(session, userName);

        // send user enter event
        String event = ".|!|.|!|ENTER|!|" + userName;
        sendEvent(roomId, event);
    }

    // send message to all user in room
    // separator = |!|
    // content : user name |!| message |!| event name |!| event parameter
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = getRoomId(session);
        String userName = userSessions.get(session);
        String m = message.getPayload();
        String content = userName + "|!|" + m;

        // press start button
        if ("|@|".equals(m)) {
            String event = ".|!|.|!|START|!|.";

            // prevent search
            try {
                StudyDto study = studyRepository.findById(Long.parseLong(roomId));
                study.setIsOpen(false);
                studyRepository.save(study);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Update study failed");
            }

            sendEvent(roomId, event);
        } else if ("|#|".equals(m)) { // press ready button
            String event = ".|!|.|!|.READY|!|" + userName;
            sendEvent(roomId, event);
        } else if ("|$|".equals(m)) { // press not ready button
            String event = ".|!|.|!|NREADY|!|" + userName;
            sendEvent(roomId, event);
        } else { // just chatting
            Set<WebSocketSession> sessions = roomSessions.get(roomId);
            if (sessions != null) {
                for (WebSocketSession webSocketSession : sessions) {
                    webSocketSession.sendMessage(new TextMessage(content));
                }
            }
        }
    }

    // disconnected
    // room id, user name manage
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = getRoomId(session);
        String userName = getUserName(session);
        userSessions.remove(session);

        Set<WebSocketSession> sessions = roomSessions.get(roomId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                roomSessions.remove(roomId);

                // send user quit event
                String event = ".|!|.|!|QUIT|!|" + userName;
                sendEvent(roomId, event);
            }
        }
    }

    private String getRoomId(WebSocketSession session) {
        UriTemplate template = new UriTemplate("/room/{roomId}/{userName}");
        Map<String, String> variables = template.match(session.getUri().getPath());
        return variables.get("roomId");
    }

    private String getUserName(WebSocketSession session) {
        UriTemplate template = new UriTemplate("/room/{roomId}/{userName}");
        Map<String, String> variables = template.match(session.getUri().getPath());
        Optional<User> user = userRepository.findByName(variables.get("userName"));

        if (user.isPresent()) {
            return user.get().getNickname();
        }

        throw new RuntimeException("Unknown user");
    }

    // send event
    // server -> client
    private void sendEvent(String roomId, String event) throws Exception {
        Set<WebSocketSession> sessions = roomSessions.get(roomId);
        if (sessions != null) {
            for (WebSocketSession webSocketSession : sessions) {
                webSocketSession.sendMessage(new TextMessage(event));
            }
        }
    }
}
