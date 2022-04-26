package es.urjc.dad.devNest.Database.Repositories;

import es.urjc.dad.devNest.Database.Entities.GamejamEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamejamRepository extends JpaRepository<GamejamEntity,Long> {
    @Cacheable
    List<GamejamEntity> findAll();

    @Cacheable
    Optional<GamejamEntity> findById(Long id);

    @Cacheable
    List<GamejamEntity> findAllById(Iterable<Long> ids);

    @CacheEvict(allEntries = true)
    GamejamEntity save(GamejamEntity gamejamEntity);

    @CacheEvict(allEntries = true)
    List<GamejamEntity> saveAll(List<? extends GamejamEntity> entities);
    
    @CacheEvict(allEntries = true)
    void delete(GamejamEntity entity);

    @CacheEvict(allEntries = true)
    void deleteById(Long id);

    @CacheEvict(allEntries = true)
    void deleteAll(Iterable<? extends GamejamEntity> entities);    

    @CacheEvict(allEntries = true)
    void deleteAll(); 

    @CacheEvict(allEntries = true)
    void deleteAllById(Iterable<? extends Long> ids);
}
