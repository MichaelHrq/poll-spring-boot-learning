package com.michael.learning.repositories;

import com.michael.learning.documents.Votos;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VotosRepository extends MongoRepository<Votos, String> {
    Long countByEnqueteId(String enqueteId);

    Long countByOpcaoVotoId(String opcaoVotoId);

    Optional<Votos> findByUsuarioIdAndEnqueteId(String usuarioId, String enqueteId);
}
