package com.ssafy.iscode.study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyResponseDto {
    private Long sessionId;
    private String roomName;
    private String hostName;
    private List<String> users;
    private String roomComment;
    private List<String> rules;
    private int maxNum;
    private Boolean isOpen;
    private Boolean isGame;
}
