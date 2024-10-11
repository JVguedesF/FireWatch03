package com.example.FireWatch03.repository;

import com.example.FireWatch03.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    // Você não precisa de @Autowired aqui!
    // A injeção de dependência é feita automaticamente pelo Spring Data JPA
}