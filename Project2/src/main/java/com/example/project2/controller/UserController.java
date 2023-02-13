package com.example.project2.controller;

import com.example.project2.domain.entity.User;
import com.example.project2.domain.response.UserResponse;
import com.example.project2.exception.UserSaveFailedException;
import com.example.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/success")
    public List<User> getAllUserSuccess(){return userService.getAllUserSuccess();}
    @GetMapping("/id/{id}")
    public UserResponse getUserById(@PathVariable int id){
        User user = userService.getUserById(id);
        return UserResponse.builder()
                .message("Returning user with id " +id ).user(user).build();
    }
    @PutMapping("/success")
    public UserResponse saveAuthorSuccess(@RequestBody User user){
        userService.saveUserSuccess(user);
        return UserResponse.builder().message("Author saved, committing...").user(user).build();
    }
    @PutMapping("/fail")
    public ResponseEntity saveUserFailed(@RequestBody User user) throws UserSaveFailedException{
        userService.saveUserFailed(user);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public UserResponse login(@RequestParam String username, @RequestParam String password){
        User user = userService.getUserByUsernameAndPassword(username, password);
        if (user != null) {
            return UserResponse.builder()
                    .message("Login Successful").user(user).build();
        } else {
            return UserResponse.builder()
                    .message("Login failed. Incorrect username or password.").build();
        }
    }
}
