package com.alanensina.orderservice.controllers;

import com.alanensina.basedomains.dto.user.UserCreateRequestDTO;
import com.alanensina.basedomains.dto.user.UserCreateResponseDTO;
import com.alanensina.basedomains.dto.user.UserDTO;
import com.alanensina.orderservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserCreateResponseDTO> create(@RequestBody UserCreateRequestDTO data){
        return userService.create(data);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> list(){
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID userId){
        return userService.findById(userId);
    }
}
