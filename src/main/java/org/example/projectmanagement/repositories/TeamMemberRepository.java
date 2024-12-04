package org.example.projectmanagement.repositories;

import org.example.projectmanagement.models.TeamMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMemberRepository extends MongoRepository<TeamMember, String> {
    Optional<TeamMember> findByTeamIdAndUserId(String teamId, String userId);

}
