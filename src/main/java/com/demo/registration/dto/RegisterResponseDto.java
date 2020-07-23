package com.demo.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterResponseDto {

    private final String email;
    private final long mobileNumber;


}
