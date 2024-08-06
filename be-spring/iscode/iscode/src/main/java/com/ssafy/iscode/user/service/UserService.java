package com.ssafy.iscode.user.service;

import com.ssafy.iscode.regular.model.dto.RegularUser;
import com.ssafy.iscode.review.model.dao.ReviewRepository;
import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.Status;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.user.model.dto.UserFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //ENCRYPT PW
        return userRepository.save(user);
    }
    public int modifyUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //ENCRYPT PW
        return userRepository.modify(user);
    }
    public int deleteUser(String id){
        reviewRepository.makeUserNull(id);
        //make all review by user NULL
        return userRepository.remove(id);
    }

    public int acceptFriend(String userId,String friendUserId){
        return userRepository.accept(userId,friendUserId);
    }

    public int insertFriend(String userId, String friendUserId){
        return userRepository.saveFriend(userId,friendUserId);
    }

    public int deleteFriend(String userId, String friendUserId){
        return userRepository.removeFriend(userId,friendUserId);
    }
    public int login(String user_id, String password){
        User gUser = userRepository.findById(user_id);

        if(gUser == null) return 0;

        if(passwordEncoder.matches(password, gUser.getPassword())){
            return 1;
            //if password correct
        }
        else{
            return 0;
        }
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
