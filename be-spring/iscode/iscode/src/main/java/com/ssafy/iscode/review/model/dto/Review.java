package com.ssafy.iscode.review.model.dto;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.*;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="review_id")
    private Long rid;

    @Column(name = "review_code", columnDefinition = "LONGTEXT")
    private String content;

    //Eager for serializable
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "algo_num", nullable = false)
    private Problem problem;

    //Eager for serializable
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    public Review() {
    }

    public Review(Long rid, String content) {
        this.rid = rid;
        this.content = content;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "Review{" +
                "rid=" + rid +
                ", content='" + content + '\'' +
                ", problem=" + problem +
                ", user=" + user +
                '}';
    }
}
