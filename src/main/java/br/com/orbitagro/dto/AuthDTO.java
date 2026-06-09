package br.com.orbitagro.dto;

import jakarta.validation.constraints.*;

public class AuthDTO {

    public record LoginRequest(
        @NotBlank(message = "Username é obrigatório")
        String username,

        @NotBlank(message = "Password é obrigatório")
        String password
    ) {}

    public record LoginResponse(
        String token,
        String username,
        String role
    ) {}

    public record RegisterRequest(
        @NotBlank String username,
        @NotBlank @Size(min = 6) String password,
        String role
    ) {}
}
