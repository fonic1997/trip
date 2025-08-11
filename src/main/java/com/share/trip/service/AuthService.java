package com.share.trip.service;

import com.share.trip.auth.dto.AuthResponse;
import com.share.trip.auth.dto.LoginRequest;
import com.share.trip.auth.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
