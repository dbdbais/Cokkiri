package com.ssafy.iscode.user.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="user_friend")
public class UserFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="relation_id")
    private Long id;

    @Column(name="user_id")
    private String userId;

    @Column(name="friend_user_id")
    private String friendUserId;

    @CreatedDate
    @Column(name = "createdTime",columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime createdTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name="status")
    private Status status = Status.REQUEST;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User friendUser;


    public UserFriend() {
    }


    public UserFriend(String userId, String friendUserId, Status status) {
        this.userId = userId;
        this.friendUserId = friendUserId;
        this.status = status;
    }



    @Override
    public String toString() {
        return "UserFriend{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", status=" + status +
                '}';
    }
}
