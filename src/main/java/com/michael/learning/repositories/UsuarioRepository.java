package com.michael.learning.repositories;

import com.michael.learning.documents.Usuarios;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuarios, String> {
    Optional<Usuarios> findByUsername(String username);
}
