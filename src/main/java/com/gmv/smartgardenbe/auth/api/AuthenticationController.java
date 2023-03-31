package com.gmv.smartgardenbe.auth.api;

import com.gmv.smartgardenbe.auth.AuthenticationService;
import com.gmv.smartgardenbe.auth.api.model.AuthenticationRequest;
import com.gmv.smartgardenbe.auth.api.model.AuthenticationResponse;
import com.gmv.smartgardenbe.auth.api.model.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;


    @Operation(operationId = "userRegister", summary = "Register user", tags = {" eeee"} , description = "ddsd",
            responses = {
                    @ApiResponse(responseCode = "200", description = "пользователь успешно создан"),
                    @ApiResponse(responseCode = "403", description = "Нет прав")
            })
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    //TODO:  сделать нормально
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
