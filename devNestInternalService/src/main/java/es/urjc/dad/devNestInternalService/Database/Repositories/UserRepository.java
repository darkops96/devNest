package es.urjc.dad.devNestInternalService.Database.Repositories;

import es.urjc.dad.devNestInternalService.Database.Entities.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByAlias(String alias);
    Optional<UserEntity> findById(Long Id);
}

