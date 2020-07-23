package com.demo.registration.service;

import com.demo.registration.model.UserEntity;
import com.demo.registration.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Slf4j
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userFetchedByEmail = userRepository.findByEmail(email);
        log.info("Fetched Details: "+userFetchedByEmail);

        if (userFetchedByEmail!=null)
        {
            return new User(userFetchedByEmail.getEmail(),
                    userFetchedByEmail.getPassword(),
                    new ArrayList<>());
        }
        else {
            throw new UsernameNotFoundException("Bad Credentials");
        }


//        return new User("abc@gmail.com",passwordEncoder().encode("abhi"),new ArrayList<>());

    }


    @Bean
    private PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }

}
