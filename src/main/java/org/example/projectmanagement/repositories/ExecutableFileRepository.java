package org.example.projectmanagement.repositories;

import org.example.projectmanagement.models.ExecutableFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutableFileRepository extends MongoRepository<ExecutableFile, String> {
}
