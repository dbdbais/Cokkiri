package com.ssafy.iscode.study.controller;

import com.ssafy.iscode.study.model.dto.StudyRequestDto;
import com.ssafy.iscode.study.model.dto.StudyResponseDto;
import com.ssafy.iscode.study.service.StudyService;
import com.ssafy.iscode.websocket.handler.LobbyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/waitingroom")
public class StudyController {

    @Autowired
    private StudyService studyService;

    @Autowired
    private LobbyWebSocketHandler lobbyWebSocketHandler;

    @PostMapping("/create-room")
    public ResponseEntity<Long> createStudy(@RequestBody StudyRequestDto studyRequestDto) {
        try {
            Long sessionId = studyService.createStudy(studyRequestDto);
            return new ResponseEntity<>(sessionId, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/enter-room")
    public ResponseEntity<StudyResponseDto> enterStudy(@RequestBody StudyRequestDto studyRequestDto) {
        try {
            studyService.enterStudy(studyRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<StudyResponseDto>> getStudys(@RequestParam Map<String, String> params) {
        String roomName = params.get("roomName");
        Boolean isGame = params.get("isGame") == null ? null : Boolean.parseBoolean(params.get("isGame"));
        int page = params.get("page") == null ? 1 : Integer.parseInt(params.get("page"));

        return new ResponseEntity<>(studyService.getStudys(roomName, isGame, page), HttpStatus.OK);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<StudyResponseDto> getStudy(@PathVariable("sessionId") Long sessionId) {
        try {
            StudyResponseDto studyResponseDto = studyService.getStudy(sessionId);
            return new ResponseEntity<>(studyResponseDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-room")
    public ResponseEntity<StudyResponseDto> updateStudy(@RequestBody StudyRequestDto studyRequestDto) {

        try {
            studyService.updateStudy(studyRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/quit-room")
    public ResponseEntity<StudyResponseDto> quitStudy(@RequestBody StudyRequestDto studyRequestDto) {
        try {
            studyService.quitStudy(studyRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/invite-room")
    public ResponseEntity<StudyResponseDto> inviteRoom(@RequestBody StudyRequestDto studyRequestDto) {
        String event = ".|!|.|!|ROOM|!|" + studyRequestDto.getSessionId();

        try {
            lobbyWebSocketHandler.sendEvent(studyRequestDto.getUserName(), event);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
