package com.gmv.smartgardenbe.auth;

import com.gmv.smartgardenbe.auth.user.Role;
import com.gmv.smartgardenbe.auth.user.User;
import com.gmv.smartgardenbe.auth.api.model.AuthenticationRequest;
import com.gmv.smartgardenbe.auth.api.model.AuthenticationResponse;
import com.gmv.smartgardenbe.auth.api.model.RegisterRequest;
import com.gmv.smartgardenbe.auth.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
    var response =  userRepository.saveAndFlush(user);
    var jwtToken= jwtService.generateToken(user);

    return AuthenticationResponse.builder()
            .user(String.valueOf(response.getId()))
            .token(jwtToken)
            .refreshToken(jwtService.generateRefreshToken(user))
            .build();

    }

    public  AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(user.getId().toString())
                .refreshToken(jwtService.generateRefreshToken(user))
                .build();

    }
}
