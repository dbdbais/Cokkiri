package com.ssafy.iscode.message.controller;

import com.ssafy.iscode.message.model.dto.MessageDto;
import com.ssafy.iscode.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/roomid")
    public ResponseEntity<Long> getRoomId(
            @RequestParam String myName,
            @RequestParam String opName
            ) {
        try {
            Long roomId = messageService.getRoomId(myName, opName);
            return new ResponseEntity<>(roomId, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable("roomId") Long roomId) {
        List<MessageDto> messages = messageService.getMessagesByRoomId(roomId);

        if(messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
