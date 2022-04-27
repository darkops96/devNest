package es.urjc.dad.devNest.Database.Repositories;

import es.urjc.dad.devNest.Database.Entities.GamejamEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamejamRepository extends JpaRepository<GamejamEntity,Long> {
    @Cacheable("gamejams")
    List<GamejamEntity> findAll();

    @Cacheable("gamejams")
    Optional<GamejamEntity> findById(Long id);

    @CacheEvict(value = "gamejams", allEntries = true)
    GamejamEntity save(GamejamEntity gamejamEntity);
    
    @CacheEvict(value = "gamejams", allEntries = true)
    void delete(GamejamEntity entity);
}
