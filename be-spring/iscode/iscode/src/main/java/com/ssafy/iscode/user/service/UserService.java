package com.ssafy.iscode.user.service;

import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.Status;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.user.model.dto.UserFriend;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public User getUser(String id){
        return userRepository.findById(id);
    }
    public List<UserFriend> getUserFriends(String id){
        return userRepository.getFriendList(id);
    }

    public int insertUser(User user){
        //insert or modify
        return userRepository.save(user);
    }
    public int insertFriend(String userId, String friendUserId, Status status){
        return userRepository.saveFriend(userId,friendUserId,status);
    }
    public int login(User user){
        User gUser = userRepository.findById(user.getId());

        if(gUser == null) return 0;

        if(gUser.equals(user)){
            return 1;
            //if password correct
        }
        else{
            return 0;
        }
    }
    public int deleteUser(String id){
        return userRepository.remove(id);
    }

}
