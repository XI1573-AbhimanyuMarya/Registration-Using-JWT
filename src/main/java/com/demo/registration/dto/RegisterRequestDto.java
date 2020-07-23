package com.demo.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequestDto {

    private final String firstname;
    private final String lastname;
    private final String email;
    private final String password;
    private final long mobileNumber;


}
