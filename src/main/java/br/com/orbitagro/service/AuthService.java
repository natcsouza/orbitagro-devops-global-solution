package br.com.orbitagro.service;

import br.com.orbitagro.dto.AuthDTO;
import br.com.orbitagro.entity.Usuario;
import br.com.orbitagro.repository.UsuarioRepository;
import br.com.orbitagro.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;

    public AuthDTO.LoginResponse login(AuthDTO.LoginRequest dto) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));
        UserDetails user = userDetailsService.loadUserByUsername(dto.username());
        String token = jwtService.generateToken(user);
        Usuario usuario = repository.findByUsername(dto.username()).orElseThrow();
        return new AuthDTO.LoginResponse(token, usuario.getUsername(), usuario.getRole().name());
    }

    public String register(AuthDTO.RegisterRequest dto) {
        Usuario usuario = Usuario.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .role(dto.role() != null ? Usuario.Role.valueOf(dto.role()) : Usuario.Role.PRODUTOR)
                .build();
        repository.save(usuario);
        return "Usuário registrado com sucesso!";
    }
}
