package es.urjc.dad.devNest.Database.Repositories;

import es.urjc.dad.devNest.Database.Entities.VideogameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideogameRepository extends JpaRepository<VideogameEntity,Long> {
}
