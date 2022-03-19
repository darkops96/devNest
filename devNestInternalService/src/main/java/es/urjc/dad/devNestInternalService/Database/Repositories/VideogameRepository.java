package es.urjc.dad.devNestInternalService.Database.Repositories;

import es.urjc.dad.devNestInternalService.Database.Entities.VideogameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideogameRepository extends JpaRepository<VideogameEntity,Long> {
}
