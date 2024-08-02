package com.ssafy.iscode.regular.service;

import com.ssafy.iscode.regular.model.dao.RegularRepository;
import com.ssafy.iscode.regular.model.dao.RegularTimeRepository;
import com.ssafy.iscode.regular.model.dao.RegularUserRepository;
import com.ssafy.iscode.regular.model.dto.*;
import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

            List<RegularUser> regularUsers = regularDto.getUsers();
            int count = 0;

            for(RegularUser regularUser: regularUsers) {
                if(regularUser.getIsAccept()) {
                    count++;
                }
            }

            if(count < regularDto.getMaxNum()) {

                for (RegularUser regularUser : regularUsers) {
                    if (regularUser.getUser().equals(user)) {
                        regularUser.setIsAccept(true);
                        regularUserRepository.save(regularUser);
                        return 1;
                    }
                }

                throw new RuntimeException("User not found in list");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<RegularResponseDto> getRequest(RegularRequestDto regularRequestDto) {
        User host = userRepository.findByName(regularRequestDto.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<RegularResponseDto> list = new ArrayList<>();
        List<RegularDto> regulars = regularRepository.findByHost(host);

        for(RegularDto regular: regulars) {
            for(RegularUser regularUser: regular.getUsers()) {
                RegularResponseDto rr = new RegularResponseDto();
                rr.setSessionId(regular.getId());
                rr.setRegularName(regular.getName());

                List<String> users = new ArrayList<>();
                if(!regularUser.getIsAccept()) {
                    users.add(regularUser.getUser().getNickname());
                }

                rr.setUsers(users);

                list.add(rr);
            }
        }

        return list;
    }

    public int joinRegular(RegularRequestDto regularRequestDto) {
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

            return 1;
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

            for(RegularUser regularUser: regularDto.getUsers()) {
                if(regularUser.getUser().equals(user)) {
                    if(regularDto.getUsers().size() == 1) {
                        closeRegular(regularRequestDto.getSessionId());
                    } else if(regularDto.getHostUser().equals(user)) {
                        regularDto.setHostUser(regularDto.getUsers().get(1).getUser());

                        try {
                            regularRepository.save(regularDto);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return 0;
                        }
                    }
                    regularUserRepository.remove(regularUser.getId());

                    return 1;
                }
            }

            throw new RuntimeException("User not found in list");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public RegularResponseDto getRegular(Long sessionId) {
        RegularDto regularDto = regularRepository.findById(sessionId);

        if(regularDto == null) {
            throw new RuntimeException("Regular not found");
        }

        List<RegularUser> regularUsers = regularDto.getUsers();
        int count = 0;
        for(RegularUser regularUser: regularUsers) {
            if(regularUser.getIsAccept()) {
                count++;
            }
        }

        RegularResponseDto regularResponseDto = new RegularResponseDto(
                regularDto.getId(),
                regularDto.getName(),
                regularDto.getComment(),
                regularDto.getRule() == null ? null : List.of(regularDto.getRule().split("\\" + SEPARATOR)),
                regularDto.getLanguage(),
                regularDto.getMaxNum(),
                regularDto.getHostUser().getNickname(),
                (regularDto.getEnd()==null && regularDto.getMaxNum()>count),
                regularDto.getTimes().stream()
                        .sorted()
                        .map(RegularTimeDto::toString)
                        .collect(Collectors.toList()),
                null,
                null
        );

        List<String> users = new ArrayList<>();
        List<String> tiers = new ArrayList<>();

        for(RegularUser user: regularDto.getUsers()) {
            users.add(user.getUser().getNickname());
            tiers.add(user.getUser().getTier().toString());
        }
        regularResponseDto.setUsers(users);
        regularResponseDto.setTiers(tiers);

        return regularResponseDto;
    }

    public List<RegularResponseDto> getRegulars(String regularName, String weekday, Integer start, Integer end, int page) {
        int weekdayNum = 0;
        if (weekday != null) {
            weekdayNum = Arrays.asList(WEEKDAY).indexOf(weekday);
        }

        int offset = 6 * (page - 1);

        List<RegularResponseDto> list = new ArrayList<>();
        List<RegularDto> regulars;

        if(end == null) {
            if(start == null) {
                if(weekday == null) {
                    if(regularName == null) {
                        regulars = regularRepository.findAll(offset);
                    } else {
                        regulars = regularRepository.findByName(regularName, offset);
                    }
                } else {
                    if(regularName == null) {
                        regulars = regularRepository.findByWeekday(weekdayNum, offset);
                    } else {
                        regulars = regularRepository.findByNameWeekday(regularName, weekdayNum, offset);
                    }
                }
            } else {
                if(weekday == null) {
                    if(regularName == null) {
                        regulars = regularRepository.findByStart(start, offset);
                    } else {
                        regulars = regularRepository.findByNameStart(regularName, start, offset);
                    }
                } else {
                    if(regularName == null) {
                        regulars = regularRepository.findByWeekdayStart(weekdayNum, start, offset);
                    } else {
                        regulars = regularRepository.findByNameWeekdayStart(regularName, weekdayNum, start, offset);
                    }
                }
            }
        } else {
            if(start == null) {
                if(weekday == null) {
                    if(regularName == null) {
                        regulars = regularRepository.findByEnd(end, offset);
                    } else {
                        regulars = regularRepository.findByNameEnd(regularName, end, offset);
                    }
                } else {
                    if(regularName == null) {
                        regulars = regularRepository.findByWeekdayEnd(weekdayNum, end, offset);
                    } else {
                        regulars = regularRepository.findByNameWeekdayEnd(regularName, weekdayNum, end, offset);
                    }
                }
            } else {
                if(weekday == null) {
                    if(regularName == null) {
                        regulars = regularRepository.findByTime(start, end, offset);
                    } else {
                        regulars = regularRepository.findByNameTime(regularName, start, end, offset);
                    }
                } else {
                    if(regularName == null) {
                        regulars = regularRepository.findByWeekdayTime(weekdayNum, start, end, offset);
                    } else {
                        regulars = regularRepository.findByNameWeekdayTime(regularName, weekdayNum, start, end, offset);
                    }
                }
            }
        }

        if (regulars == null) {
            regulars = new ArrayList<>();
        }

        for(RegularDto regular: regulars) {
            List<String> users = new ArrayList<>();
            List<String> tiers = new ArrayList<>();

            for(RegularUser regularUser: regular.getUsers()) {
                if(regularUser.getIsAccept()) {
                    users.add(regularUser.getUser().getNickname());
                    tiers.add(regularUser.getUser().getTier().toString());
                }
            }

            RegularResponseDto rr = new RegularResponseDto(
                    regular.getId(),
                    regular.getName(),
                    regular.getComment(),
                    regular.getRule() == null ? null : List.of(regular.getRule().split("\\" + SEPARATOR)),
                    regular.getLanguage(),
                    regular.getMaxNum(),
                    regular.getHostUser().getNickname(),
                    (regular.getEnd()==null && regular.getMaxNum()>users.size()),
                    regular.getTimes().stream()
                            .sorted()
                            .map(RegularTimeDto::toString)
                            .collect(Collectors.toList()),
                    users,
                    tiers
            );

            list.add(rr);
        }

        return list;
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

    public int closeRegular(Long sessionId) {
        RegularDto regularDto = regularRepository.findById(sessionId);

        Date now = new Date();

        regularDto.setEnd(now);

        try {
            regularRepository.save(regularDto);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
