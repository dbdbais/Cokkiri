package com.ssafy.iscode.study.service;

import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.study.model.dao.StudyRepository;
import com.ssafy.iscode.study.model.dao.StudyUserRepository;
import com.ssafy.iscode.study.model.dto.StudyDto;
import com.ssafy.iscode.study.model.dto.StudyRequestDto;
import com.ssafy.iscode.study.model.dto.StudyResponseDto;
import com.ssafy.iscode.study.model.dto.StudyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudyService {
    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyUserRepository studyUserRepository;

    @Autowired
    private UserRepository userRepository;

    private final String SEPARATOR = "|";

    public Long createStudy(StudyRequestDto studyRequestDto) {
        StudyDto studyDto = new StudyDto();

        studyDto.setName(studyRequestDto.getRoomName());
        studyDto.setComment(studyRequestDto.getRoomComment());
        studyDto.setRule(String.join(SEPARATOR, studyRequestDto.getRules()));
        studyDto.setMaxNum(studyRequestDto.getMaxNum());
        studyDto.setIsOpen(studyRequestDto.getIsOpen());
        studyDto.setIsGame(studyRequestDto.getIsGame());

        User hostUser = userRepository.findByName(studyRequestDto.getUserName())
                .orElseThrow(() -> new RuntimeException("Host user not found"));

        try {
            studyDto.setHostUser(hostUser);
            studyRepository.save(studyDto);

            StudyUser users = new StudyUser();
            users.setStudy(studyDto);
            users.setUser(hostUser);
            studyUserRepository.save(users);

            return studyDto.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void enterStudy(StudyRequestDto studyRequestDto) {
        StudyDto studyDto = studyRepository.findById(studyRequestDto.getSessionId());

        if (studyDto == null) {
            throw new RuntimeException("Study not found");
        }

        User user = userRepository.findByName(studyRequestDto.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<StudyUser> users = studyDto.getUsers();
        if (users.size() < studyDto.getMaxNum()) {
            try {
                StudyUser studyUser = new StudyUser();
                studyUser.setStudy(studyDto);
                studyUser.setUser(user);
                studyUserRepository.save(studyUser);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Fail to save");
            }
        } else {
            throw new RuntimeException("Users List is full");
        }

        return;
    }

    public StudyResponseDto getStudy(Long sessionId) {
        StudyDto studyDto = studyRepository.findById(sessionId);

        if(studyDto == null) {
            throw new RuntimeException("Study not found");
        }

        StudyResponseDto studyResponseDto = new StudyResponseDto();
        studyResponseDto.setRoomName(studyDto.getName());
        studyResponseDto.setHostName(studyDto.getHostUser().getNickname());
        studyResponseDto.setRoomComment(studyDto.getComment());
        studyResponseDto.setRules(List.of(studyDto.getRule().split("\\" + SEPARATOR)));
        studyResponseDto.setMaxNum(studyDto.getMaxNum());
        studyResponseDto.setIsOpen(studyDto.getIsOpen());
        studyResponseDto.setIsGame(studyDto.getIsGame());

        studyResponseDto.setUsers(new ArrayList<>());
        for(StudyUser user: studyDto.getUsers()) {
            studyResponseDto.getUsers().add(user.getUser().getNickname());
        }

        return studyResponseDto;
    }

    public int updateStudy(StudyRequestDto studyRequestDto) {
        StudyDto studyDto = studyRepository.findById(studyRequestDto.getSessionId());

        if (studyDto == null) {
            throw new RuntimeException("Study not found");
        }

        try {
            studyDto.setName(studyRequestDto.getRoomName());
            studyDto.setComment(studyRequestDto.getRoomComment());
            studyDto.setRule(String.join(SEPARATOR, studyRequestDto.getRules()));
            studyDto.setMaxNum(studyRequestDto.getMaxNum());
            studyDto.setIsOpen(studyRequestDto.getIsOpen());

            studyRepository.save(studyDto);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int quitStudy(StudyRequestDto studyRequestDto) {
        StudyDto studyDto = studyRepository.findById(studyRequestDto.getSessionId());

        if (studyDto == null) {
            throw new RuntimeException("Study not found");
        }

        User user = userRepository.findByName(studyRequestDto.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<StudyUser> users = studyDto.getUsers();

        for (StudyUser studyUser: users) {
            if (studyUser.getUser().getId().equals(user.getId())) {
                if(users.size() == 1) { // nobody here
                    closeStudy(studyDto.getId());
                } else if (studyDto.getHostUser().getId().equals(user.getId())) { // host change
                    User nextHost = users.get(0).getUser();
                    studyDto.setHostUser(nextHost);

                    try {
                        studyRepository.save(studyDto);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
                studyUserRepository.remove(studyUser.getId());
                return 1;
            }
        }

        return 0;
    }

    public List<StudyResponseDto> getStudys(String roomName, Boolean isGame, int page) {
        int offset = 6 * (page - 1);

        List<StudyResponseDto> list = new ArrayList<>();
        List<StudyDto> studys;

        if(isGame == null) {
            if(roomName == null) {
                studys = studyRepository.findAll(offset);
            } else {
                studys = studyRepository.findByName(roomName, offset);
            }
        } else {
            if(roomName == null) {
                studys = studyRepository.findSelectedAll(isGame, offset);
            } else {
                studys = studyRepository.findSelectedByName(roomName, isGame, offset);
            }
        }

        if (studys == null) {
            studys = new ArrayList<>();
        }

        for(StudyDto study: studys) {
            List<String> users = new ArrayList<>();

            for(StudyUser user: study.getUsers()) {
                users.add(user.getUser().getNickname());
            }

            StudyResponseDto sr = new StudyResponseDto(
                    study.getId(),
                    study.getName(),
                    study.getHostUser().getNickname(),
                    users,
                    study.getComment(),
                    List.of(study.getRule().split("\\" + SEPARATOR)),
                    study.getMaxNum(),
                    study.getIsOpen(),
                    study.getIsGame()
            );

            list.add(sr);
        }

        return list;
    }

    public int closeStudy(Long sessionId) {
        StudyDto studyDto = studyRepository.findById(sessionId);

        Date now = new Date();

        studyDto.setEnd(now);

        // prevent search
        studyDto.setIsOpen(false);

        try {
            studyRepository.save(studyDto);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public StudyDto getStudyDto(Long sessionId) {
        return studyRepository.findById(sessionId);
    }
}
