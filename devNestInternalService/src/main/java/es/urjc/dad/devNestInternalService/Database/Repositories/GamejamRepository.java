package es.urjc.dad.devNestInternalService.Database.Repositories;

import es.urjc.dad.devNestInternalService.Database.Entities.GamejamEntity;
import es.urjc.dad.devNestInternalService.Database.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GamejamRepository extends JpaRepository<GamejamEntity,Long> {
    Optional<GamejamEntity> findById(Long Id);

}
