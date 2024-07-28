package com.ssafy.iscode.user.model.dto;

import jakarta.persistence.*;

@Entity
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

    @Enumerated(value = EnumType.STRING)
    @Column(name="status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_user_id", insertable = false, updatable = false)
    private User friendUser;

    public UserFriend() {
    }

    public UserFriend(Long id, String userId, String friendUserId, Status status, User user, User friendUser) {
        this.id = id;
        this.userId = userId;
        this.friendUserId = friendUserId;
        this.status = status;
        this.user = user;
        this.friendUser = friendUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(String friendUserId) {
        this.friendUserId = friendUserId;
    }

    public User getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(User friendUser) {
        this.friendUser = friendUser;
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
