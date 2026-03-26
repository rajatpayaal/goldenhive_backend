package com.goldenhive.backend.iservice;

import com.goldenhive.backend.dto.AuthResponse;
import com.goldenhive.backend.dto.LoginRequest;
import com.goldenhive.backend.dto.ProfileDTO;
import com.goldenhive.backend.dto.RegisterRequest;
import com.goldenhive.backend.entity.User;

public interface IAuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse adminLogin(LoginRequest request);
    ProfileDTO getProfile(String email);
    User loadUserByEmail(String email);
}
