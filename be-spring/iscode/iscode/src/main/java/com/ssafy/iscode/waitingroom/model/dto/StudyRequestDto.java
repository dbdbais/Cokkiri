package com.ssafy.iscode.waitingroom.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyRequestDto {
    private Long sessionId;
    private String roomName;
    private String userName;
    private String roomComment;
    private List<String> rules;
    private int maxNum;
    private Boolean isOpen;
    private Boolean isGame;
}
