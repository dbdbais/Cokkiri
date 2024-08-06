package com.ssafy.iscode.user.controller;

import com.ssafy.iscode.user.model.dto.Status;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.user.model.dto.UserFriend;
import com.ssafy.iscode.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userapi")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService us) {
        this.userService = us;
    }

    @PostMapping("/register")
    public int regist(@RequestBody User user){
        return userService.insertUser(user);
    }
    @GetMapping()
    public List<User> getAllUser(){
        return userService.getUsers();
    }
    @GetMapping("{id}")
    public User getUser(@PathVariable String id){
        return userService.getUser(id);
    }
    @GetMapping("/friends/{id}")
    public List<UserFriend> getFriends(@PathVariable String id){
        return userService.getUserFriends(id);
    }

    //get friend request to friendUserId
    @PostMapping("/friends")
    public int insertRelation(@RequestParam String userId,
                              @RequestParam String friendUserId){
        return userService.insertFriend(userId,friendUserId);
    }

    //accept friend
    @PutMapping("/friends/accept")
    public int acceptFriends(@RequestParam String userId,
                            @RequestParam String friendUserId){
        return userService.acceptFriend(friendUserId,userId);
    }
    //delete friend request to friendUserId
    @DeleteMapping("/friends")
    public int deleteRelation(@RequestParam String userId,
                              @RequestParam String friendUserId){
        return userService.deleteFriend(userId,friendUserId);
    }
    @PostMapping("/login")
    public int login(@RequestParam String user_id,
                     @RequestParam String password){
        return userService.login(user_id,password);
    }
    @PutMapping("/modify")
    public int modify(@RequestBody User user){
        return userService.modifyUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public int delUser(@PathVariable String id){
        return userService.deleteUser(id);
    }

    @GetMapping("/regular/{userName}")
    public ResponseEntity<List<Long>> getRegulaer(@PathVariable("userName") String userName) {
        try {
            List<Long> list = userService.getRegular(userName);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}