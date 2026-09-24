package com.michael.learning.repositories;

import com.michael.learning.documents.OpcoesVotos;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OpcaoVotoRepository extends MongoRepository<OpcoesVotos, String> {
    List<OpcoesVotos> getAllByEnqueteId(String enqueteId);

    List<OpcoesVotos> findAllByEnqueteId(String enqueteId);
}
