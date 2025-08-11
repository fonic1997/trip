package com.share.trip.service;

import com.share.trip.auth.dto.AuthResponse;
import com.share.trip.auth.dto.LoginRequest;
import com.share.trip.auth.dto.RegisterRequest;
import com.share.trip.common.exception.InvalidCredentialsException;
import com.share.trip.common.exception.UserAlreadyExistsException;
import com.share.trip.model.User;
import com.share.trip.repository.UserRepository;
import com.share.trip.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service

public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }
        User u = new User();
        u.setFullName(request.getFullName());
        u.setEmail(request.getEmail().toLowerCase());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setPhoneNumber(request.getPhoneNumber());
        u.setRole(request.getRole());
        userRepository.save(u);

        String token = jwtUtil.generateToken(u.getId().toString(), u.getRole());
        return new AuthResponse(token, "User registered successfully");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Optional<User> ou = userRepository.findByEmail(request.getEmail().toLowerCase());
        if (ou.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        User u = ou.get();
        if (!passwordEncoder.matches(request.getPassword(), u.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(u.getId().toString(), u.getRole());
        return new AuthResponse(token, "Login successful");
    }

}

