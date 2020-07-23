package com.demo.registration.controller;

import com.demo.registration.dto.LoginRequestDto;
import com.demo.registration.dto.LoginResponseDto;
import com.demo.registration.security.jwt.JwtUtil;
import com.demo.registration.service.MyUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        } catch (BadCredentialsException exp) {
            throw new Exception("Bad Credentials:---- " + exp);
        }
        final UserDetails userDetails = myUserDetailService.loadUserByUsername(loginRequestDto.getEmail());
        String generatedToken = jwtUtil.generateToken(userDetails);

        log.info("Token Generated:-   " + generatedToken);
        return ResponseEntity.ok(new LoginResponseDto(generatedToken));

    }
}
