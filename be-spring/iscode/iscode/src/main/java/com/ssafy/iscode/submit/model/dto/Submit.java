package com.ssafy.iscode.submit.model.dto;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="submit")
@Getter
@Setter
public class Submit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="submit_id")
    private Long no;

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

    public Submit(Problem problem, User user, String submit_code, Boolean correct) {
        this.problem = problem;
        this.user = user;
        this.submit_code = submit_code;
        this.correct = correct;
    }
}
