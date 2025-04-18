package com.example.FireWatch03.services;

import com.example.FireWatch03.domain.models.UserAutenticator;
import com.example.FireWatch03.repositories.UserAutenticatorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserAutenticatorRepository repository;

    public AuthorizationService(UserAutenticatorRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserAutenticator user = (UserAutenticator) repository.findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + login);
        }

        return user;
    }
}