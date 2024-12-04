package org.example.projectmanagement.repositories;

import org.example.projectmanagement.models.Version;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionRepository extends MongoRepository<Version, String> {
}
