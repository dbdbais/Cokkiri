package com.ssafy.iscode.submit.model.dto;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.study.model.dto.StudyDto;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.*;

@Entity
@Table(name="submit")
public class Submit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="submit_id")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="study_id")
    private StudyDto studyDto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "algo_num")
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="code", columnDefinition = "LONGTEXT")
    private String submit_code;

    @Column(name="correct")
    private Boolean correct;

    public Submit() {
    }

    public Submit(StudyDto studyDto, Problem problem, User user, String submit_code, Boolean correct) {
        this.studyDto = studyDto;
        this.problem = problem;
        this.user = user;
        this.submit_code = submit_code;
        this.correct = correct;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public StudyDto getStudyDto() {
        return studyDto;
    }

    public void setStudyDto(StudyDto studyDto) {
        this.studyDto = studyDto;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubmit_code() {
        return submit_code;
    }

    public void setSubmit_code(String submit_code) {
        this.submit_code = submit_code;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "Submit{" +
                "no=" + no +
                ", studyDto=" + studyDto +
                ", problem=" + problem +
                ", user=" + user +
                ", submit_code='" + submit_code + '\'' +
                ", correct=" + correct +
                '}';
    }

}
