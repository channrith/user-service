package com.futureflowhome.userservice.service;

import com.futureflowhome.userservice.dto.LoginRequest;
import com.futureflowhome.userservice.dto.LoginResponse;
import com.futureflowhome.userservice.entity.Role;
import com.futureflowhome.userservice.entity.User;
import com.futureflowhome.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsernameIgnoreCase(request.getUsername())
                .orElseThrow(() -> new org.springframework.security.authentication.BadCredentialsException("Invalid username or password"));
        if (!user.isEnabled() || !user.isAccountNonLocked()) {
            throw new org.springframework.security.authentication.BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new org.springframework.security.authentication.BadCredentialsException("Invalid username or password");
        }
        Role role = user.getRole();
        String roleName = role != null ? role.getName() : "ROLE_USER";
        String token = jwtService.createToken(user.getUsername(), user.getId(), roleName);
        return new LoginResponse(token, jwtService.getExpirationSeconds());
    }
}
