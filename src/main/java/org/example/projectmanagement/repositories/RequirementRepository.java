package org.example.projectmanagement.repositories;

import org.example.projectmanagement.models.Requirement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequirementRepository extends MongoRepository<Requirement, String> {
}
