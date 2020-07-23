package com.demo.registration.service;

import com.demo.registration.dto.RegisterRequestDto;
import com.demo.registration.model.UserEntity;
import com.demo.registration.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserEntity saveUser(RegisterRequestDto registerRequestDto)
    {

    UserEntity userEntity=new UserEntity();
    userEntity.setFirstname(registerRequestDto.getFirstname());
    userEntity.setLastname(registerRequestDto.getLastname());
    userEntity.setEmail(registerRequestDto.getEmail());
    userEntity.setMobileNumber(registerRequestDto.getMobileNumber());
    userEntity.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
    return userRepository.save(userEntity);

    }

}
