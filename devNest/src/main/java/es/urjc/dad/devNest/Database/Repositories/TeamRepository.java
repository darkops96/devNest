package es.urjc.dad.devNest.Database.Repositories;

import java.util.List;
import java.util.Optional;

import es.urjc.dad.devNest.Database.Entities.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<TeamEntity,Long> {

    public List<TeamEntity> findByMembersId(long id);
    public Optional<TeamEntity> findByTeamName(String teamName);
}
