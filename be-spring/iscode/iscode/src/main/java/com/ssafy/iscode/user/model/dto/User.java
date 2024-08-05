package com.ssafy.iscode.user.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.iscode.regular.model.dto.RegularUser;
import jakarta.persistence.*;


import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="user")
public class User {
    @Id
    @Column(name = "user_id")
    private String id;
    @Column(name="nick_name")
    private String nickname;
    // might be using Spring Security later..
    @Column(name = "user_password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tier")
    private Tier tier = Tier.SEED;

    @Column(name ="user_percent")
    private double percent= 0.0;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<RegularUser> regulars;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Avoid serialization of the full list of friends
    private Set<UserFriend> friends = new HashSet<>();


    // Add a friend with status
    public void addFriend(User friend, Status status) {
        UserFriend userFriend = new UserFriend(this.id, friend.getId(), status);
        this.friends.add(userFriend);
        UserFriend reverseRealtion = new UserFriend(friend.getId(),this.id,status);
        friend.getFriends().add(reverseRealtion);
    }

    // Remove a friend
    public void removeFriend(User friend) {

        // Remove the friend relationship from the other user
        friend.getFriends().removeIf(userFriend -> userFriend.getFriendUserId().equals(this.id));

        // Remove the friend relationship from this user
        friends.removeIf(userFriend -> userFriend.getFriendUserId().equals(friend.getId()));


    }

    public User() {

    }

    public User(String id, String nickname, String password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
    }

    public User(String id, String nickname, String password, Tier tier, double percent) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.tier = tier;
        this.percent = percent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public List<RegularUser> getRegulars() {
        return regulars;
    }

    public void setRegulars(List<RegularUser> regulars) {
        this.regulars = regulars;
    }


    public Set<UserFriend> getFriends() {
        return friends;
    }

    public void setFriends(Set<UserFriend> friends) {
        this.friends = friends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", tier=" + tier +
                ", percent=" + percent +
                ", regulars=" + regulars +
                ", friends=" + friends +
                '}';
    }
}
