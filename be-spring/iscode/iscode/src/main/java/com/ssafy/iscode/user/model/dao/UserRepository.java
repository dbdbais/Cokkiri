package com.ssafy.iscode.user.model.dao;

import com.ssafy.iscode.mission.model.dto.MissionType;
import com.ssafy.iscode.mission.service.MissionService;
import com.ssafy.iscode.user.model.dto.Status;
import com.ssafy.iscode.user.model.dto.Tier;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.user.model.dto.UserFriend;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final EntityManager em;
    private final MissionService missionService;

    public UserRepository(EntityManager em, MissionService missionService) {
        this.em = em;
        this.missionService = missionService;
    }

    @Transactional
    public int save(User user) {
        try {

            if(findById(user.getId()) == null){

                MissionType dailyMS = missionService.assignRandomMission();
                //Insert
                user.setMission(dailyMS,false);
                em.persist(user);
            }
            else{
                return 0;
            }
            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // failed
        }
    }

    @Transactional
    public int modify(User user) {
        try {

            if(findById(user.getId()) != null){
                //Modify
                em.merge(user);
            }
            else{
                return 0;
            }
            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // failed
        }
    }

    @Transactional
    public boolean isMissionAccomplished(String userId) {

        User aUser = findById(userId);
        Optional<MissionType> missionType = aUser.getSingleMissionType();

        if(missionType.isPresent()){

            String sql = missionType.get().getSql();
            System.out.println(sql);
            Long result = (Long) em.createQuery(sql)
                    .setParameter("userId", userId)
                    .getSingleResult();

            return result > 0;
        }
        else{
            return false;
        }
    }




    @Transactional
    public int saveFriend(String userId, String friendUserId) {
        try {
            // Find both users
            User user = findById(userId);
            User friendUser = findById(friendUserId);

            if (user == null || friendUser == null) {
                return 0; // User or Friend not found
            }
            System.out.println(user);
            System.out.println(friendUser);

            // Add friend relationships
            user.addFriend(friendUser);

            // Save changes to the database
            em.merge(user); // Save the user with updated friends list
            em.merge(friendUser); // Save the friend with updated friends list

            return 1; // Successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Failed
        }
    }

    @Transactional
    public int accept(String userId, String friendUserId){
        try {
            // Find both users
            User user = findById(userId);
            User friendUser = findById(friendUserId);

            if ( user.acceptFriend(friendUser)) {
                // Save changes to the database
                em.merge(user); // Save the user with updated friends list
                em.merge(friendUser); // Save the friend with updated friends list

                return 1; // Successfully saved
            }
            else{
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Failed
        }
    }

    @Transactional
    public int removeFriendRelationship(String userId, String friendUserId) {
        try {
            int deletedCount = em.createQuery(
                            "DELETE FROM UserFriend uf WHERE uf.user.id = :userId AND uf.friendUserId = :friendUserId")
                    .setParameter("userId", userId)
                    .setParameter("friendUserId", friendUserId)
                    .executeUpdate();

            return deletedCount; // Return the number of deleted relationships
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Failed
        }
    }

    @Transactional
    public int removeAllFriendRelationship(String userId) {
        try {
            int deletedCount = em.createQuery(
                            "DELETE FROM UserFriend uf WHERE uf.user.id = :userId OR uf.friendUserId = :userId")
                    .setParameter("userId", userId)
                    .executeUpdate();

            return deletedCount; // Return the number of deleted relationships
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Failed
        }
    }

    @Transactional
    public int removeFriend(String userId, String friendUserId) {
        try {
            // Find both users
            User user = findById(userId);
            User friendUser = findById(friendUserId);

            if (user == null || friendUser == null) {
                return 0; // User or Friend not found
            }

            // Remove friend relationships
            removeFriendRelationship(userId, friendUserId);
            removeFriendRelationship(friendUserId, userId);

            // Save changes to the database
            em.merge(user); // Save the user with updated friends list
            em.merge(friendUser); // Save the friend with updated friends list

            return 1; // Successfully removed
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Failed
        }
    }

    @Transactional
    //get All FriendList
    public List<UserFriend> getFriendList(String id){
        return em.createQuery(
                        "SELECT uf FROM UserFriend uf WHERE uf.user.id = :userId", UserFriend.class)
                .setParameter("userId", id)
                .getResultList();
    }
    @Transactional
    public int scoreAchieved(String id, int pLevel){
        try{
            User aUser = findById(id);
            //get achieved User
            System.out.println(aUser);

            if(aUser.getScore() == 500){
                return 0;
            }
            else{
                Tier uTier = aUser.getTier();
                aUser.setScore(Math.min(aUser.getScore() + pLevel,500));
                //max is 500
                aUser.updateTier();
                //tier update
                em.merge(aUser);
                return 1;
            }
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    @Transactional
    public User findById(String id){
        return em.find(User.class,id);
    }

    @Transactional
    public int remove(String id){

        User delUser = findById(id);
        if(delUser != null){

            removeAllFriendRelationship(delUser.getId());
            //delete FreindShip

            em.remove(delUser);
            return 1;
            // successfully deleted
        }
        else{
            return 0;
            // failed
        }
    }

    @Transactional
    public List<User> findAll(){
        return em.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Transactional
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
