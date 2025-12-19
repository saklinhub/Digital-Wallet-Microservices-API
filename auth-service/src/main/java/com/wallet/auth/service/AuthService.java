package com.wallet.auth.service;

import com.wallet.auth.entity.UserCredential;
import com.wallet.auth.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    public String generateToken(String username) {
        UserCredential user = repository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
        return jwtService.generateToken(username, user.getId());
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
