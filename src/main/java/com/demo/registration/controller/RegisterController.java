package com.demo.registration.controller;

import com.demo.registration.dto.RegisterRequestDto;
import com.demo.registration.dto.RegisterResponseDto;
import com.demo.registration.model.UserEntity;
import com.demo.registration.service.RegisterUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RegisterController {
    @Autowired
    private RegisterUserService registerUserService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(RegisterRequestDto registerRequestDto) {
        UserEntity userEntity = registerUserService.saveUser(registerRequestDto);
        log.info("user entity: "+userEntity.toString());
        return ResponseEntity.ok(new RegisterResponseDto(userEntity.getEmail(), userEntity.getMobileNumber()));


    }

}
