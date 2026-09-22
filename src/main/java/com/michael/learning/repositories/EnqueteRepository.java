package com.michael.learning.repositories;

import com.michael.learning.documents.Enquetes;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnqueteRepository extends MongoRepository<Enquetes, String> {
}
