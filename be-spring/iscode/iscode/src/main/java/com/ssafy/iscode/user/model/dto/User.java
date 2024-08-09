package com.ssafy.iscode.user.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.iscode.regular.model.dto.RegularUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
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

    @Column(name ="user_score")
    private int score= 0;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<RegularUser> regulars;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Avoid serialization of the full list of friends
    private Set<UserFriend> friends = new HashSet<>();

    // Add a friend with status
    public void addFriend(User friend) {
        UserFriend userFriend = new UserFriend(this.id, friend.getId(), Status.REQUEST);
        this.friends.add(userFriend);
        UserFriend reverseRealtion = new UserFriend(friend.getId(),this.id,Status.SELECT);
        friend.getFriends().add(reverseRealtion);
    }

    // Remove a friend
    public void removeFriend(User friend) {

        // Remove the friend relationship from the other user
        friend.getFriends().removeIf(userFriend -> userFriend.getFriendUserId().equals(this.id));

        // Remove the friend relationship from this user
        friends.removeIf(userFriend -> userFriend.getFriendUserId().equals(friend.getId()));
    }

    // Accept a friend request
    public boolean acceptFriend(User friend) {
        UserFriend userFriend = findFriend(friend.getId(), Status.REQUEST);

        UserFriend friendUserFriend = friend.findFriend(this.id, Status.SELECT);

        System.out.println(userFriend);
        System.out.println(friendUserFriend);
        if (userFriend != null && friendUserFriend != null) {
            userFriend.setStatus(Status.ACCEPT);
            friendUserFriend.setStatus(Status.ACCEPT);

            return true;
        }
        return false;
    }

    // Find a UserFriend object with a specific friend ID and status
    private UserFriend findFriend(String friendId, Status status) {
        return friends.stream()
                .filter(userFriend -> userFriend.getFriendUserId().equals(friendId) && userFriend.getStatus() == status)
                .findFirst()
                .orElse(null);
    }

    public void updateTier() {
        int score = this.score;
        Tier newTier;

        if (score >= 500) {
            newTier = Tier.DURIAN;
        } else if (score >= 400) {
            newTier = Tier.MELON;
        } else if (score >= 300) {
            newTier = Tier.ORANGE;
        } else if (score >= 200) {
            newTier = Tier.APPLE;
        } else if (score >= 100) {
            newTier = Tier.KIWI;
        } else {
            newTier = Tier.SEED;
        }
        //if Tier is different
        if(this.getTier() != newTier){
            this.setTier(newTier);
        }

    }

    public User() {

    }

    public User(String id, String nickname, String password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
    }

    public User(String id, String nickname, String password, Tier tier, int score, List<RegularUser> regulars, Set<UserFriend> friends) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.tier = tier;
        this.score = score;
        this.regulars = regulars;
        this.friends = friends;
    }




    @Override
    public int hashCode() {
        return Objects.hash(id, password);
    }


}
