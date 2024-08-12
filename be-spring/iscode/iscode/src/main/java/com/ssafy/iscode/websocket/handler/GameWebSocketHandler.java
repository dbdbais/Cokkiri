package com.ssafy.iscode.websocket.handler;

import com.ssafy.iscode.game.model.dto.GameDto;
import com.ssafy.iscode.game.service.GameService;
import com.ssafy.iscode.study.service.StudyService;
import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.study.model.dao.StudyRepository;
import com.ssafy.iscode.study.model.dto.StudyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriTemplate;

import java.util.*;

public class GameWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, Set<WebSocketSession>> roomSessions = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, WebSocketSession> userSessions = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Long> roomEnterTimes = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, List<Map<String, Long>>> roomPrices = Collections.synchronizedMap(new HashMap<>());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private StudyService studyService;

    // connected
    // room id, user name, start time manage
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = getRoomId(session);
        String userName = getUserName(session);

        roomSessions.computeIfAbsent(roomId, k -> Collections.synchronizedSet(new HashSet<>())).add(session);
        userSessions.put(userName, session);

        if(!roomEnterTimes.containsKey(roomId)) {
            Date now = new Date();
            long millis = now.getTime();
            roomEnterTimes.put(roomId, millis);
        }

        if(!roomPrices.containsKey(roomId)) {
            List<Map<String, Long>> list = new ArrayList<>();
            roomPrices.put(roomId, list);
        }
    }

    // send message to all user in room
    // separator = |!|
    // content : user name |!| message |!| event name |!| event parameter
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = getRoomId(session);
        String userName = getUserName(session);
        String[] m = message.getPayload().split("\\|!\\|");
        long startTime = roomEnterTimes.get(roomId);

        // code success
        if ("|@|".equals(m[0])) {
            if(m.length == 1) {
                if (roomPrices.get(roomId).size() < 3) {
                    Date now = new Date();
                    long nowMillis = now.getTime();

                    long diff = nowMillis - startTime;

                    GameDto gameDto = gameService.getGame(Long.parseLong(roomId));

                    gameService.inputRankUser(Long.parseLong(roomId), userName, gameDto.getPrizes().size() + 1, diff);

                    Map<String, Long> input = new HashMap<>();
                    input.put(userName, diff);

                    roomPrices.get(roomId).add(input);
                    String content = roomPrices.get(roomId).size() + "|!|" + userName + "|!|" + diff;

                    Set<WebSocketSession> sessions = roomSessions.get(roomId);
                    if (sessions != null) {
                        for (WebSocketSession webSocketSession : sessions) {
                            webSocketSession.sendMessage(new TextMessage(content));
                        }
                    }
                }
            } else {
                roomPrices.remove(roomId);

                String content = "END|!|.";

                Set<WebSocketSession> sessions = roomSessions.get(roomId);
                if (sessions != null) {
                    for (WebSocketSession webSocketSession : sessions) {
                        webSocketSession.sendMessage(new TextMessage(content));
                    }
                }
            }
        } else {
            String opUserName = m[1];
            String event = "";

            if ("|#|".equals(m[0])) { // used item 1 (blind problem)
                event = "BLIND|!|.";
            } else if ("|$|".equals(m[0])) { // used item 2 (minimum size of submit button)
                event = "SIZE|!|.";
            } else if ("|%|".equals(m[0])) { // used item 3 (prevent code input)
                event = "PREVENT|!|.";
            } else if ("|^|".equals(m[0])) {
                event = "BIG|!|.";
            } else if ("|&|".equals(m[0])) {
                event = "SMALL|!|.";
            } else {
                return;
            }

            WebSocketSession opSession = userSessions.get(opUserName);
            opSession.sendMessage(new TextMessage(event));
        }
    }

    // disconnected
    // room id, user name, start time, price manage
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
                roomEnterTimes.remove(roomId);
                roomPrices.remove(roomId);
                studyService.closeStudy(Long.parseLong(roomId));
            }

            String event = "EXIT|!|" + userName;
            for (WebSocketSession webSocketSession : sessions) {
                webSocketSession.sendMessage(new TextMessage(event));
            }
        }
    }

    private String getRoomId(WebSocketSession session) {
        UriTemplate template = new UriTemplate("/socket/game/{roomId}/{userName}");
        Map<String, String> variables = template.match(session.getUri().getPath());
        return variables.get("roomId");
    }

    private String getUserName(WebSocketSession session) {
        UriTemplate template = new UriTemplate("/socket/game/{roomId}/{userName}");
        Map<String, String> variables = template.match(session.getUri().getPath());
        Optional<User> user = userRepository.findByName(variables.get("userName"));

        if (user.isPresent()) {
            return user.get().getNickname();
        }

        throw new RuntimeException("Unknown user");
    }
}
