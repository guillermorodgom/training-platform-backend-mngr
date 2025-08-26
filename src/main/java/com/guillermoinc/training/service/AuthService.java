package com.guillermoinc.training.service;

import com.guillermoinc.training.dto.LoginRequestDto;
import com.guillermoinc.training.dto.LoginResponseDto;
import com.guillermoinc.training.dto.RegisterRequestDto;

public interface AuthService {
    
    LoginResponseDto login(LoginRequestDto request);
    
    LoginResponseDto register(RegisterRequestDto request);
}