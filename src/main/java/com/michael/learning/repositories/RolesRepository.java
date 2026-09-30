package com.michael.learning.repositories;

import com.michael.learning.documents.Roles;
import com.michael.learning.enums.RolesEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RolesRepository extends MongoRepository<Roles, String> {
    Optional<Roles> findByRole(RolesEnum rolesEnum);
}
