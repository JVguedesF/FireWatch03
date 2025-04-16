package com.example.FireWatch03.controllers;

import com.example.FireWatch03.domain.repositories.UserAutenticatorRepository;
import com.example.FireWatch03.dto.*;
import com.example.FireWatch03.domain.services.TokenService;
import com.example.FireWatch03.domain.models.UserAutenticator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/FireWatch03")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserAutenticatorRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((UserAutenticator) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            // Trata erro de autenticação (ex: credenciais inválidas)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        if (repository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Login já cadastrado!"));
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserAutenticator newUser = new UserAutenticator(data.login(), encryptedPassword, data.role());

        try {
            UserAutenticator savedUser = repository.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            // Trata erro de persistência (ex: violação de chave única)
            return ResponseEntity.badRequest().body(new ErrorDTO("Erro ao registrar usuário."));
        }
    }
}