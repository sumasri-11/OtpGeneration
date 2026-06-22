package com.example.OtpGenaration.controller;

import com.example.OtpGenaration.entity.User;
import com.example.OtpGenaration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getUserById(
            @PathVariable Long id) {

        return userService.findUserById(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(
            @PathVariable String email) {

        return userService.getUserByEmail(email);
    }

    @PutMapping
    public User updateUser(
            @RequestBody User user) {

        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return "User Deleted Successfully";
    }
}