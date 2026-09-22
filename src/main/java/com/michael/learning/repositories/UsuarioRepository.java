package com.michael.learning.repositories;

import com.michael.learning.documents.Usuarios;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuarios, String> {
    Optional<Usuarios> findByUsername(String username);
}
