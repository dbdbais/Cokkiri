package com.ssafy.iscode.review.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDTO {

    private String code;

    private String content;

    private Long problem;

    private String user;

    public ReviewRequestDTO(String code, String content, Long problem, String user) {
        this.code = code;
        this.content = content;
        this.problem = problem;
        this.user = user;
    }

    @Override
    public String toString() {
        return "ReviewRequestDTO{" +
                "content='" + content + '\'' +
                ", problem=" + problem +
                ", user='" + user + '\'' +
                '}';
    }
}
