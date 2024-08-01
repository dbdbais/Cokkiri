package com.ssafy.iscode.waitingroom.controller;

import com.ssafy.iscode.waitingroom.model.dto.StudyRequestDto;
import com.ssafy.iscode.waitingroom.model.dto.StudyResponseDto;
import com.ssafy.iscode.waitingroom.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waitingroom")
public class StudyController {

    @Autowired
    private StudyService studyService;

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

    @PostMapping("/quit-room/")
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
}
