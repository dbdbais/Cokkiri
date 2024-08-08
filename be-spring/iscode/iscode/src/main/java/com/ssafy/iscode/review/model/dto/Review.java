package com.ssafy.iscode.review.model.dto;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="review_id")
    private Long rid;

    @Column(name = "review_code", columnDefinition = "LONGTEXT")
    private String code;

    @Column(name = "review_content", columnDefinition = "LONGTEXT")
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
