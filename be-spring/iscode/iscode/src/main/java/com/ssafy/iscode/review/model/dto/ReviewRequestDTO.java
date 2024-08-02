package com.ssafy.iscode.review.model.dto;


public class ReviewRequestDTO {
    private String content;

    private Long problem;

    private String user;

    public ReviewRequestDTO(String content, Long problem, String user) {
        this.content = content;
        this.problem = problem;
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getProblem() {
        return problem;
    }

    public void setProblem(Long problem) {
        this.problem = problem;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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
