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

@Schema(description = "register request")
public class RegisterRequest {
    @Schema(description = "Имя пользователя", example = "Ivan")
    private String firstName;
    @Schema(description = "Фамилия пользователя", example = "Ivanov")
    private String lastName;
    @Schema(description = "Почта", example = "ivan@gmail.com")
    private String email;
    @Schema(description = "Пароль", example = "secret")
    private String password;

}
