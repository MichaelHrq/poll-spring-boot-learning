package com.michael.learning.repositories;

import com.michael.learning.documents.Votos;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VotosRepository extends MongoRepository<Votos, String> {
}
