package com.wallet.user.controller;

import com.wallet.user.entity.User;
import com.wallet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return service.getUser(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }
}
