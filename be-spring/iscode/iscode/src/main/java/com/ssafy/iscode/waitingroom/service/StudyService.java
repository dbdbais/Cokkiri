package com.ssafy.iscode.waitingroom.service;

import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.waitingroom.model.dao.StudyRepository;
import com.ssafy.iscode.waitingroom.model.dto.StudyDto;
import com.ssafy.iscode.waitingroom.model.dto.StudyRequestDto;
import com.ssafy.iscode.waitingroom.model.dto.StudyResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyService {
    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private UserRepository userRepository;

    private final String SEPARATOR = "|";

    public Long createStudy(StudyRequestDto studyRequestDto) {
        StudyDto studyDto = new StudyDto();

        studyDto.setName(studyRequestDto.getRoomName());
        studyDto.setComment(studyRequestDto.getRoomComment());
        studyDto.setRule(String.join(SEPARATOR, studyRequestDto.getRules()));
        studyDto.setOpen(studyRequestDto.isOpen());
        studyDto.setGame(studyRequestDto.isGame());
u
        User hostUser = userRepository.findByName(studyRequestDto.getUserName())
                .orElseThrow(() -> new RuntimeException("Host user not found"));

        studyDto.setHostUser(hostUser);
        studyDto.getUsers().add(hostUser);

        StudyDto resultStudy = studyRepository.save(studyDto);
        return resultStudy.getId();
    }

    public void enterStudy(StudyRequestDto studyRequestDto) {
        StudyDto studyDto = studyRepository.findById(studyRequestDto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Study not found"));

        User user = userRepository.findByName(studyRequestDto.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> users = studyDto.getUsers();
        if (users.size() < 6) {
            studyDto.getUsers().add(user);
            studyRepository.save(studyDto);
        } else {
            throw new RuntimeException("Users List is full");
        }

        return;
    }

    public StudyResponseDto getStudy(Long sessionId) {
        StudyDto studyDto = studyRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Study not found"));

        StudyResponseDto studyResponseDto = new StudyResponseDto();
        studyResponseDto.setRoomName(studyDto.getName());
        studyResponseDto.setHostName(studyDto.getHostUser().getName());
        studyResponseDto.setRoomComment(studyDto.getComment());
        studyResponseDto.setRules(List.of(studyDto.getRule().split(SEPARATOR)));
        studyResponseDto.setOpen(studyDto.isOpen());
        studyResponseDto.setGame(studyDto.isGame()
        studyResponseDto.setUsers(studyDto.getUsers());

        return studyResponseDto;
    }

    public void updateStudy(StudyRequestDto studyRequestDto) {
        StudyDto studyDto = studyRepository.findById(studyRequestDto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Study not found"));

        studyDto.setName(studyRequestDto.getRoomName());
        studyDto.setComment(studyRequestDto.getRoomComment());
        studyDto.setRule(String.join(SEPARATOR, studyRequestDto.getRules()));
        studyDto.setOpen(studyRequestDto.isOpen());

        return;
    }

    public void quitStudy(StudyRequestDto studyRequestDto) {
        StudyDto studyDto = studyRepository.findById(studyRequestDto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Study not found"));

        User user = userRepository.findByName(studyRequestDto.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(studyDto.getUsers().remove(user)) {
            studyRepository.save(studyDto);
        } else {
            throw new RuntimeException("User not found");
        }

        return;
    }
}
