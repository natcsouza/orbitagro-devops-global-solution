package br.com.orbitagro.controller;

import br.com.orbitagro.dto.AuthDTO;
import br.com.orbitagro.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Login e registro de usuários")
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    @Operation(summary = "Fazer login e obter token JWT")
    public ResponseEntity<AuthDTO.LoginResponse> login(@Valid @RequestBody AuthDTO.LoginRequest dto) {
        return ResponseEntity.ok(service.login(dto));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário")
    public ResponseEntity<String> register(@Valid @RequestBody AuthDTO.RegisterRequest dto) {
        return ResponseEntity.ok(service.register(dto));
    }
}
