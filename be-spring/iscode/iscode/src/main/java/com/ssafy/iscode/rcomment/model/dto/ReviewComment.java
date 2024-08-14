package com.ssafy.iscode.rcomment.model.dto;

import com.ssafy.iscode.review.model.dto.Review;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rComment")
public class ReviewComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long cid;

    @Column(nullable = false,length = 1000)
    private String content;

    @ColumnDefault("FALSE")
    @Column(nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private ReviewComment parent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User writer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id")
    private Review review;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<ReviewComment> children;

    @CreatedDate
    @Column(name = "createdTime",columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime createdTime;

    public ReviewComment() {
    }

    public ReviewComment(Long cid, String content, boolean isDeleted, ReviewComment parent, User writer, Review review, List<ReviewComment> children) {
        this.cid = cid;
        this.content = content;
        this.isDeleted = isDeleted;
        this.parent = parent;
        this.writer = writer;
        this.review = review;
        this.children = children;
    }

    public ReviewCommentResponseDTO toResponseDTO() {
        ReviewCommentResponseDTO dto = new ReviewCommentResponseDTO(this.cid, this.writer, this.content,this.isDeleted);
        if (this.children != null) {
            for (ReviewComment child : this.children) {
                dto.getChildren().add(child.toResponseDTO());
            }
        }
        return dto;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public ReviewComment getParent() {
        return parent;
    }

    public void setParent(ReviewComment parent) {
        this.parent = parent;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public List<ReviewComment> getChildren() {
        return children;
    }

    public void setChildren(List<ReviewComment> children) {
        this.children = children;
    }
}
