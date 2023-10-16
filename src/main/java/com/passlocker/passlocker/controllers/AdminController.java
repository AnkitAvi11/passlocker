package com.passlocker.passlocker.controllers;

import com.passlocker.passlocker.controllers.responses.GenericPaginatedResponse;
import com.passlocker.passlocker.entities.UserEntity;
import com.passlocker.passlocker.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    //  function to get all the user details (with pagination)
    @GetMapping("/users")
    public GenericPaginatedResponse getAllUsers(
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int offset,
            HttpServletRequest request
    ) {
        return this.userService.getUsers(limit, offset, request);
    }

    //  function to get a single user details
    @GetMapping("/users/{user_id}")
    public UserEntity getUser(@PathVariable(name = "user_id") String userId) {
        return this.userService
                .getUserByUserId(userId);
    }
}
