package com.michael.learning.repositories;

import com.michael.learning.documents.OpcoesVotos;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OpcaoVotoRepository extends MongoRepository<OpcoesVotos, String> {
}
