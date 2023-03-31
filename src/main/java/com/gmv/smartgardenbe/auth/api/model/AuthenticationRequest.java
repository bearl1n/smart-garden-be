package com.gmv.smartgardenbe.auth.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Schema(description = "Почта", example = "ivan@gmail.com")
    private String email;
    @Schema(description = "Пароль", example = "secret")
    private String password;
}
