package com.ssafy.iscode.webrtc.controller;

import com.google.gson.JsonObject;
import com.ssafy.iscode.study.service.StudyService;
import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/session")
@CrossOrigin("*")
public class WebrtcController {

    @Autowired
    private StudyService studyService;

    private OpenVidu openVidu;

    private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
    private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

    private String OPENVIDU_URL;
    private String SECRET;

    public WebrtcController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
        System.out.println(this.openVidu==null);
    }

    @PostMapping(value = "/get-token")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody Map<String, Object> params) {

        //
        String sessionId = (String) params.get("sessionId");
        String userName = (String) params.get("userName");

        // logging
        System.out.println(
                "Getting sessionId and token | {sessionId}=" + params.get("sessionId")
                + ", {userName}=" + params.get("userName")
        );

        // 유저 역할 부여
        OpenViduRole role = OpenViduRole.PUBLISHER;

        // Build connectionProperties object with the serverData and the role
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
                .role(role).build();

        // END

        Map<String, String> response = new HashMap<>();

        if (this.mapSessions.get(sessionId) != null) {
            // Session already exists
            System.out.println("Existing session " + sessionId);
            try {

                // Generate a new token with the recently created connectionProperties
                String token = this.mapSessions.get(sessionId).createConnection(connectionProperties).getToken();

                // Update our collection storing the new token
                this.mapSessionNamesTokens.get(sessionId).put(token, role);

                // Prepare the response with the token
                response.put("0", token);

                // Return the response to the client
                return new ResponseEntity<>(response, HttpStatus.OK);

            } catch (OpenViduJavaClientException e1) {
                // If internal error generate an error message and return it to client
                return getErrorResponse(e1);
            } catch (OpenViduHttpException e2) {
                if (404 == e2.getStatus()) {
                    // Invalid sessionId (user left unexpectedly). Session object is not valid
                    // anymore. Clean collections and continue as new session
                    this.mapSessions.remove(sessionId);
                    this.mapSessionNamesTokens.remove(sessionId);
                }
            }
        }

        // New session
        System.out.println("New session " + sessionId);
        try {

            // Create a new OpenVidu Session
            Session session = this.openVidu.createSession();
            // Generate a new token with the recently created connectionProperties
            String token = session.createConnection(connectionProperties).getToken();
            System.out.println("session : "+session + "/n token: "+token);
            // Store the session and the token in our collections
            this.mapSessions.put(sessionId, session);
            this.mapSessionNamesTokens.put(sessionId, new ConcurrentHashMap<>());
            this.mapSessionNamesTokens.get(sessionId).put(token, role);

            // Prepare the response with the sessionId and the token
            response.put("0", token);

            // Return the response to the client
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            // If error generate an error message and return it to client
            return getErrorResponse(e);
        }
    }

    @PostMapping(value = "/remove-user")
    public ResponseEntity<Map<String, String>> removeUser(@RequestBody Map<String, Object> sessionNameToken) throws Exception {

        System.out.println("Removing user | {sessionId, token}=" + sessionNameToken);

        // Retrieve the params from BODY
        String sessionId = (String) sessionNameToken.get("sessionId");
        String token = (String) sessionNameToken.get("token");

        // If the session exists
        if (this.mapSessions.get(sessionId) != null && this.mapSessionNamesTokens.get(sessionId) != null) {

            // If the token exists
            if (this.mapSessionNamesTokens.get(sessionId).remove(token) != null) {
                // User left the session
                if (this.mapSessionNamesTokens.get(sessionId).isEmpty()) {
                    // Last user left: session must be removed
                    this.mapSessions.remove(sessionId);

                    studyService.closeStudy(Long.parseLong(sessionId));
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // The TOKEN wasn't valid
                System.out.println("Problems in the app server: the TOKEN wasn't valid");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            // The SESSION does not exist
            System.out.println("Problems in the app server: the SESSION does not exist");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/close-session")
    public ResponseEntity<JsonObject> closeSession(@RequestBody Map<String, Object> sessionId) throws Exception {

        System.out.println("Closing session | {sessionId}=" + sessionId);

        // Retrieve the param from BODY
        String session = (String) sessionId.get("sessionId");

        // If the session exists
        if (this.mapSessions.get(session) != null && this.mapSessionNamesTokens.get(session) != null) {
            Session s = this.mapSessions.get(session);
            s.close();
            this.mapSessions.remove(session);
            this.mapSessionNamesTokens.remove(session);

            studyService.closeStudy(Long.parseLong(session));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // The SESSION does not exist
            System.out.println("Problems in the app server: the SESSION does not exist");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/force-disconnect")
    public ResponseEntity<Map<String , String>> forceDisconnect(@RequestBody Map<String, Object> params) {
        try {
            // Retrieve the param from BODY
            String session = (String) params.get("sessionName");
            String connectionId = (String) params.get("connectionId");

            // If the session exists
            if (this.mapSessions.get(session) != null && this.mapSessionNamesTokens.get(session) != null) {
                Session s = this.mapSessions.get(session);
                s.forceDisconnect(connectionId);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // The SESSION does not exist
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            e.printStackTrace();
            return getErrorResponse(e);
        }
    }

    @DeleteMapping(value = "/force-unpublish")
    public ResponseEntity<Map<String , String>> forceUnpublish(@RequestBody Map<String, Object> params) {
        try {
            // Retrieve the param from BODY
            String session = (String) params.get("sessionName");
            String streamId = (String) params.get("streamId");

            // If the session exists
            if (this.mapSessions.get(session) != null && this.mapSessionNamesTokens.get(session) != null) {
                Session s = this.mapSessions.get(session);
                s.forceUnpublish(streamId);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // The SESSION does not exist
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            e.printStackTrace();
            return getErrorResponse(e);
        }
    }

    private ResponseEntity<Map<String, String>> getErrorResponse(Exception e) {
        Map<String, String> json = new HashMap<>();
        json.put("cause", e.getCause().toString());
        json.put("error", e.getMessage());
        json.put("exception", e.getClass().getCanonicalName());
        return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
