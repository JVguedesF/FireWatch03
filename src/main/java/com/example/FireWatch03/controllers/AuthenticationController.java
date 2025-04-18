package com.example.FireWatch03.controllers;

import com.example.FireWatch03.domain.dto.ApiResponse;
import com.example.FireWatch03.domain.dto.AuthenticationDTO;
import com.example.FireWatch03.domain.dto.ErrorDTO;
import com.example.FireWatch03.domain.dto.LoginResponseDTO;
import com.example.FireWatch03.domain.dto.RegisterDTO;
import com.example.FireWatch03.exceptions.UserAlreadyExistsException;
import com.example.FireWatch03.exceptions.RegistrationFailedException;
import com.example.FireWatch03.repositories.UserAutenticatorRepository;
import com.example.FireWatch03.services.TokenService;
import com.example.FireWatch03.domain.models.UserAutenticator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/FireWatch03")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserAutenticatorRepository repository;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserAutenticatorRepository repository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((UserAutenticator) auth.getPrincipal());
            return ResponseEntity.ok(new ApiResponse<>(true, new LoginResponseDTO(token), "Login bem-sucedido"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, null, "Credenciais inválidas"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserAutenticator> register(@RequestBody @Valid RegisterDTO data) {
        if (repository.findByLogin(data.login()) != null) {
            throw new UserAlreadyExistsException("Login já cadastrado!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserAutenticator newUser = new UserAutenticator(data.login(), encryptedPassword, data.role());

        try {
            UserAutenticator savedUser = repository.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            throw new RegistrationFailedException("Erro ao registrar usuário.", e);
        }
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleUserAlreadyExists(UserAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<ErrorDTO> handleRegistrationFailed(RegistrationFailedException e) {
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
    }
}