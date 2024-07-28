package com.ssafy.iscode.message.service;

import com.ssafy.iscode.message.model.dao.MessageRepository;
import com.ssafy.iscode.message.model.dao.MessageRoomRepository;
import com.ssafy.iscode.message.model.dao.MessageRoomUserRepository;
import com.ssafy.iscode.message.model.dto.MessageDto;
import com.ssafy.iscode.message.model.dto.MessageRoomDto;
import com.ssafy.iscode.message.model.dto.MessageRoomUser;
import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageRoomRepository messageRoomRepository;

    @Autowired
    private MessageRoomUserRepository messageRoomUserRepository;

    @Autowired
    private UserRepository userRepository;


    public Long getRoomId(String userName1, String userName2) {
        User user1 = userRepository.findByName(userName1)
                .orElseThrow(() -> new RuntimeException("User not found, " + userName1));
        User user2 = userRepository.findByName(userName2)
                .orElseThrow(() -> new RuntimeException("User not found: " + userName2));

        Optional<Long> roomId = messageRoomRepository.findRoom(user1.getId(), user2.getId());

        if (roomId.isPresent()) {
            // room id already exist
            return roomId.get();
        } else {
            // room id not exist
            // create new message room => return room id
            MessageRoomDto messageRoomDto = new MessageRoomDto();
            messageRoomDto = messageRoomRepository.save(messageRoomDto);

            MessageRoomUser roomUser1 = new MessageRoomUser();
            roomUser1.setRoom(messageRoomDto);
            roomUser1.setUser(user1);
            messageRoomUserRepository.save(roomUser1);
            MessageRoomUser roomUser2 = new MessageRoomUser();
            roomUser2.setRoom(messageRoomDto);
            roomUser2.setUser(user2);
            messageRoomUserRepository.save(roomUser2);

            return messageRoomDto.getId();
        }
    }

    // find all messages by room id
    public List<MessageDto> getMessagesByRoomId(Long roomId) {
        return messageRepository.findAllByRoomId(roomId);
    }

    // save message
    // not use in controller => use in websocket hander
    public void saveMessage(Long roomId, String userName, String content) {
        MessageRoomDto messageRoomDto = messageRoomRepository.findById(roomId);

        if (messageRoomDto == null) {
            throw new RuntimeException("Room not found");
        }

        User sender = userRepository.findByName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MessageDto messageDto = new MessageDto();
        messageDto.setMessageRoom(messageRoomDto);
        messageDto.setSender(sender);
        messageDto.setContent(content);

        messageRepository.save(messageDto);
    }
}
