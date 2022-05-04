package es.urjc.dad.devNest.Database.Repositories;

import es.urjc.dad.devNest.Database.Entities.GamejamEntity;

import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

public interface GamejamRepository extends JpaRepository<GamejamEntity, Long> {
    @Cacheable(value = "gamejams", key = "#id")    
    @Override
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<GamejamEntity> findById(Long id);

    @CachePut(value = "gamejams", key = "#gamejamEntity.id")    
    @Override
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    GamejamEntity save(GamejamEntity gamejamEntity);

    @CacheEvict(value = "gamejams", key = "#entity.id")
    void delete(GamejamEntity entity);
}
