package com.passlocker.passlocker.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.passlocker.passlocker.controllers.requests.UserUpdateRequest;
import com.passlocker.passlocker.entities.UserEntity;
import com.passlocker.passlocker.exceptions.BadPermissionException;
import com.passlocker.passlocker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Path for creating new user account
     * @param userEntity request body
     * @return the user entity created
     * @throws BadPermissionException when unauthorized user tries to create a new account
     */
    @PostMapping("")
    public ResponseEntity<UserEntity> signupUser(@RequestBody UserEntity userEntity) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.userService
                        .createUser(userEntity));
    }

    @GetMapping("/me")
    public UserEntity getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String username = authentication.getName();

        return this.userService.getUserByUsername(username);
    }

    @PatchMapping("/update")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserUpdateRequest userRequest) {
        return null;
    }

    @PostMapping("/reset-password")
    public ResponseEntity<UserEntity> resetPassword(@RequestBody JsonNode jsonNode) {
        return null;
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteUserAccount() {
        return null;
    }
}
