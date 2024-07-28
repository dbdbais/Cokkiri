package com.ssafy.iscode.user.controller;

import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/login")
    public int login(@RequestBody User user){
        return userService.login(user);
    }
    @PutMapping("/modify")
    public int modify(@RequestBody User user){
        return userService.insertUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public int delUser(@PathVariable String id){
        return userService.deleteUser(id);
    }

}
