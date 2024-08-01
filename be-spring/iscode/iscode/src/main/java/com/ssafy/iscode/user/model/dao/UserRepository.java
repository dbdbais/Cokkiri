package com.ssafy.iscode.user.model.dao;

import com.ssafy.iscode.user.model.dto.Status;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.user.model.dto.UserFriend;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserRepository {

    private final EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public int save(User user) {
        try {

            if(findById(user.getId()) == null){
                //Insert
                em.persist(user);
            }
            else{
                //Modify
                em.merge(user);
            }
            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // failed
        }
    }
    public int saveFriend(String userId, String friendUserId, Status status) {
        try {
            User user = findById(userId);
            User friendUser = findById(friendUserId);

            if (user == null || friendUser == null) {
                return 0; // User or Friend not found
            }

            // Check if the friendship already exists
            UserFriend existingFriend = em.createQuery(
                            "SELECT uf FROM UserFriend uf WHERE uf.user.id = :userId AND uf.friendUser.id = :friendUserId",
                            UserFriend.class)
                    .setParameter("userId", userId)
                    .setParameter("friendUserId", friendUserId)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existingFriend == null) {
                // Create a new UserFriend entity
                UserFriend userFriend = new UserFriend();
                userFriend.setUser(user);
                userFriend.setUserId(userId); // 본인의 ID
                userFriend.setFriendUser(friendUser);
                userFriend.setFriendUserId(friendUserId); // 친구의 ID
                userFriend.setStatus(status);
                em.persist(userFriend);
            } else {
                // Update the existing UserFriend entity
                existingFriend.setStatus(status);
                em.merge(existingFriend);
            }

            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // failed
        }
    }
    //get All FriendList
    public List<UserFriend> getFriendList(String id){
        return em.createQuery(
                        "SELECT uf FROM UserFriend uf WHERE uf.user.id = :userId", UserFriend.class)
                .setParameter("userId", id)
                .getResultList();
    }

    public User findById(String id){
        return em.find(User.class,id);
    }

    public int remove(String id){

        User delUser = findById(id);
        if(delUser != null){
            em.remove(delUser);
            return 1;
            // successfully deleted
        }
        else{
            return 0;
            // failed
        }
    }

    public List<User> findAll(){
        return em.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    public Optional<User> findByName(String nickname) {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.nickname = :nickname", User.class)
                    .setParameter("nickname", nickname)
                    .getSingleResult();
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty(); // Return an empty Optional if no result is found
        }
    }
}
