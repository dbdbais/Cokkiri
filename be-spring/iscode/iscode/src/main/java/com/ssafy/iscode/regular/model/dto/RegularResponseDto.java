package com.ssafy.iscode.regular.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegularResponseDto {
    private Long sessionId;
    private String regularName;
    private String regularComment;
    private List<String> rules;
    private String language;
    private int maxNum;
    private String hostName;
    private Boolean isRecruitment;
    private List<String> times;
    private List<String> users;
    private List<String> tiers;
}
