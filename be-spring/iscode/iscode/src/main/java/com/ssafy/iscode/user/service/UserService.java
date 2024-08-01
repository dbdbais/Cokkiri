package com.ssafy.iscode.user.service;

import com.ssafy.iscode.reguler.model.dto.RegularUser;
import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.Status;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.user.model.dto.UserFriend;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Long> getRegular(String userName) {
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Long> list = new ArrayList<>();

        for(RegularUser regular: user.getRegulars()) {
            if(regular.getIsAccept()) {
                list.add(regular.getRegular().getId());
            }
        }

        return list;
    }
}
