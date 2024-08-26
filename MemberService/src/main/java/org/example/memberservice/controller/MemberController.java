package org.example.memberservice.controller;

import org.example.memberservice.dto.ModifyUserDto;
import org.example.memberservice.dto.RegisterUserDto;
import org.example.memberservice.entity.UserEntity;
import org.example.memberservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    @Autowired
    UserService userService;

    @PostMapping("/member/users/registration")
    public UserEntity registerUser(@RequestBody RegisterUserDto dto) {
        return userService.registerUser(dto.loginId, dto.userName);
    }

    @PutMapping("/member/users/{userId}/modify")
    public UserEntity modifyUser(@PathVariable Long userId,  ModifyUserDto dto) {
        return userService.modifyUser(userId, dto.userName);
    }

    @PostMapping("/member/users/{loginId}/login")
    public UserEntity login(@PathVariable String loginId) {
        return userService.getUser(loginId);
    }

}
