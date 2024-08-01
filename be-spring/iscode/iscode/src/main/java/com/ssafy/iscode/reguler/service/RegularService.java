package com.ssafy.iscode.reguler.service;

import com.ssafy.iscode.reguler.model.dao.RegularRepository;
import com.ssafy.iscode.reguler.model.dao.RegularTimeRepository;
import com.ssafy.iscode.reguler.model.dao.RegularUserRepository;
import com.ssafy.iscode.reguler.model.dto.*;
import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegularService {

    @Autowired
    private RegularRepository regularRepository;

    @Autowired
    private RegularTimeRepository regularTimeRepository;

    @Autowired
    private RegularUserRepository regularUserRepository;

    @Autowired
    private UserRepository userRepository;

    private final String SEPARATOR = "|";
    private final String[] WEEKDAY = {"mon", "tue", "wed", "thu", "fir", "sat", "sun"};

    public Long createRegular(RegularRequestDto regularRequestDto) {
        User host = userRepository.findByName(regularRequestDto.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            RegularDto regularDto = new RegularDto();
            regularDto.setName(regularRequestDto.getRegularName());
            regularDto.setComment(regularRequestDto.getRegularComment());
            regularDto.setMaxNum(regularRequestDto.getMaxNum());
            regularDto.setHostUser(host);
            regularRepository.save(regularDto);

            RegularUser regularUser = new RegularUser();
            regularUser.setRegular(regularDto);
            regularUser.setUser(host);
            regularUser.setIsAccept(true);
            regularUserRepository.save(regularUser);

            saveTime(regularDto, regularRequestDto.getTimes());

            return regularDto.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int updateRegular(RegularRequestDto regularRequestDto) {
        RegularDto regularDto = regularRepository.findById(regularRequestDto.getSessionId());

        if(regularDto == null) {
            throw new RuntimeException("Regular not found");
        }

        try {
            regularDto.setName(regularRequestDto.getRegularName());
            regularDto.setComment(regularRequestDto.getRegularComment());
            regularDto.setRule(String.join(SEPARATOR, regularRequestDto.getRules()));
            regularDto.setLanguage(regularRequestDto.getLanguage());
            regularDto.setMaxNum(regularRequestDto.getMaxNum());
            regularRepository.save(regularDto);

            for(RegularTimeDto time: regularDto.getTimes()) {
                regularTimeRepository.remove(time.getId());
            }
            saveTime(regularDto, regularRequestDto.getTimes());

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int enterRegular(RegularRequestDto regularRequestDto) {
        User user = userRepository.findByName(regularRequestDto.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            RegularDto regularDto = regularRepository.findById(regularRequestDto.getSessionId());

            if(regularDto == null) {
                throw new RuntimeException("Regular not found");
            }

            for(RegularUser regularUser: regularDto.getUsers()) {
                if(regularUser.getUser().equals(user)) {
                    regularUser.setIsAccept(true);
                    regularUserRepository.save(regularUser);
                    return 1;
                }
            }

            throw new RuntimeException("User not found in list");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int joinReqular(RegularRequestDto regularRequestDto) {
        User user = userRepository.findByName(regularRequestDto.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            RegularDto regularDto = regularRepository.findById(regularRequestDto.getSessionId());

            if(regularDto == null) {
                throw new RuntimeException("Regular not found");
            }

            RegularUser regularUser = new RegularUser();
            regularUser.setRegular(regularDto);
            regularUser.setUser(user);
            regularUser.setIsAccept(false);
            regularUserRepository.save(regularUser);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int quitRegular(RegularRequestDto regularRequestDto) {
        User user = userRepository.findByName(regularRequestDto.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            RegularDto regularDto = regularRepository.findById(regularRequestDto.getSessionId());

            if(regularDto == null) {
                throw new RuntimeException("Regular not found");
            }

            List<RegularUser> users = regularDto.getUsers();
            for(RegularUser regularUser: users) {
                if(regularUser.getUser().equals(user)) {
                    regularUserRepository.remove(regularUser.getId());

                    if(regularDto.getHostUser().equals(user)) {
                        regularDto.setHostUser(users.get(0).getUser());
                        regularRepository.save(regularDto);
                    }
                    return 1;
                }
            }

            throw new RuntimeException("User not found in list");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private RegularResponseDto getRegular(RegularRequestDto regularRequestDto) {
        RegularDto regularDto = regularRepository.findById(regularRequestDto.getSessionId());

        if(regularDto == null) {
            throw new RuntimeException("Regular not found");
        }

        RegularResponseDto regularResponseDto = new RegularResponseDto(
                regularDto.getId(),
                regularDto.getName(),
                regularDto.getComment(),
                List.of(regularDto.getRule().split("\\" + SEPARATOR)),
                regularDto.getLanguage(),
                regularDto.getMaxNum(),
                regularDto.getHostUser().getNickname(),
                (regularDto.getEnd()==null && regularDto.getMaxNum()==regularDto.getUsers().size()),
                regularDto.getTimes().stream()
                        .map(RegularTimeDto::toString)
                        .collect(Collectors.toList()),
                null
        );

        List<String> users = new ArrayList<>();

        for(RegularUser user: regularDto.getUsers()) {
            users.add(user.getUser().getNickname());
        }
    }

    private void saveTime(RegularDto regularDto, List<String> times) {

        try {
            for (String timeStr : times) {

                List<String> timeDetail = List.of(timeStr.split("\\" + SEPARATOR));

                RegularTimeDto regularTimeDto = new RegularTimeDto();
                regularTimeDto.setRegular(regularDto);
                regularTimeDto.setWeekday(Arrays.asList(WEEKDAY).indexOf(timeDetail.get(0)));
                regularTimeDto.setStart(Integer.parseInt(timeDetail.get(1)));
                regularTimeDto.setEnd(Integer.parseInt(timeDetail.get(2)));
                regularTimeRepository.save(regularTimeDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
