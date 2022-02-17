package es.urjc.dad.devNest.Database.Repositories;

import es.urjc.dad.devNest.Database.Entities.GamejamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamejamRepository extends JpaRepository<GamejamEntity,Long> {
}
